package kz.bitlab.lms.service.impl;

import jakarta.annotation.PostConstruct;
import kz.bitlab.lms.dto.user.RoleAssignRequest;
import kz.bitlab.lms.dto.user.UserRegisterRequest;
import kz.bitlab.lms.dto.user.UserUpdateRequest;
import kz.bitlab.lms.exception.enums.ExceptionStatus;
import kz.bitlab.lms.exception.LmsException;
import kz.bitlab.lms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin-username}")
    private String adminUsername;

    @Value("${keycloak.admin-password}")
    private String adminPassword;

    private Keycloak keycloak;

    @PostConstruct
    public void init() {
        this.keycloak = KeycloakBuilder.builder()
                .serverUrl(authServerUrl)
                .realm("master")
                .clientId("admin-cli")
                .username(adminUsername)
                .password(adminPassword)
                .build();
    }

    @Override
    public void registerUser(UserRegisterRequest request) {
        RealmResource realmResource = keycloak.realm(realm);
        UsersResource usersResource = realmResource.users();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEnabled(true);

        Response response = usersResource.create(user);

        if (response.getStatus() != 201) {
            throw new LmsException("Failed to create user in Keycloak, status: " + response.getStatus(), ExceptionStatus.USER_REGISTRATION_FAILED);
        }

        String userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.password());

        UserResource userResource = usersResource.get(userId);
        userResource.resetPassword(credential);

        if (request.roles() != null && !request.roles().isEmpty()) {
            List<RoleRepresentation> roleReps = request.roles().stream()
                    .map(roleName -> realmResource.roles().get(roleName).toRepresentation())
                    .collect(Collectors.toList());
            userResource.roles().realmLevel().add(roleReps);
        }
    }

    @Override
    public void updateUser(String username, UserUpdateRequest request) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(username, true);
        if (users.isEmpty()) {
            throw new LmsException("User not found: " + username, ExceptionStatus.USER_NOT_FOUND);
        }

        UserRepresentation user = users.getFirst();
        UserResource userResource = realmResource.users().get(user.getId());

        boolean changed = false;
        if (request.firstName() != null) {
            user.setFirstName(request.firstName());
            changed = true;
        }
        if (request.lastName() != null) {
            user.setLastName(request.lastName());
            changed = true;
        }

        if (changed) {
            userResource.update(user);
        }

        if (request.password() != null && !request.password().isEmpty()) {
            CredentialRepresentation credential = new CredentialRepresentation();
            credential.setTemporary(false);
            credential.setType(CredentialRepresentation.PASSWORD);
            credential.setValue(request.password());
            userResource.resetPassword(credential);
        }
    }

    @Override
    public void assignRoles(String targetUsername, RoleAssignRequest request) {
        RealmResource realmResource = keycloak.realm(realm);
        List<UserRepresentation> users = realmResource.users().search(targetUsername, true);
        if (users.isEmpty()) {
            throw new LmsException("User not found: " + targetUsername, ExceptionStatus.USER_NOT_FOUND);
        }

        UserResource userResource = realmResource.users().get(users.getFirst().getId());

        List<RoleRepresentation> roleReps = request.roles().stream()
                .map(roleName -> realmResource.roles().get(roleName).toRepresentation())
                .collect(Collectors.toList());

        userResource.roles().realmLevel().add(roleReps);
    }
}
