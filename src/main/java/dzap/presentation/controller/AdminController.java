package dzap.presentation.controller;

import dzap.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Contrôleur exemple pour les endpoints admin
 * Tous les endpoints ici nécessitent le rôle ADMIN
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Endpoints réservés aux administrateurs")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    @GetMapping("/dashboard")
    @Operation(summary = "Dashboard admin", description = "Accéder au dashboard administrateur")
    public ResponseEntity<Map<String, Object>> getDashboard(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(Map.of(
            "message", "Bienvenue sur le dashboard admin",
            "admin", user.getEmail(),
            "role", user.getRole().name()
        ));
    }

    @GetMapping("/test")
    @Operation(summary = "Test admin", description = "Endpoint de test pour vérifier l'accès admin")
    public ResponseEntity<Map<String, String>> testAdmin() {
        return ResponseEntity.ok(Map.of(
            "status", "success",
            "message", "Vous avez accès aux fonctionnalités admin"
        ));
    }
}

