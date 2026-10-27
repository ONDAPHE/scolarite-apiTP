package com.tp.scolarite;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class EtudiantControllerTest {

    @Mock
    EtudiantRepository repository;

    @InjectMocks
    EtudiantController controller;

    @Test
    void listerRetourneLesEtudiants() {
        when(repository.findAll())
            .thenReturn(List.of(new Etudiant("Mballa", "Jean", "jean@mail.cm")));

        List<Etudiant> resultat = controller.lister();

        assertEquals(1, resultat.size());
        assertEquals("Mballa", resultat.get(0).getNom());
    }

    @Test
    void trouverRetourneLEtudiantExistant() {
        when(repository.findById(1L))
            .thenReturn(Optional.of(new Etudiant("Mballa", "Jean", "jean@mail.cm")));

        ResponseEntity<Etudiant> reponse = controller.trouver(1L);

        assertEquals(200, reponse.getStatusCode().value());
        assertEquals("Mballa", reponse.getBody().getNom());
    }

    @Test
    void trouverRetourne404SiInconnu() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertEquals(404, controller.trouver(99L).getStatusCode().value());
    }

    @Test
    void creerEnregistreLEtudiant() {
        Etudiant etudiant = new Etudiant("Ngono", "Marie", "marie@mail.cm");
        when(repository.save(etudiant)).thenReturn(etudiant);

        Etudiant resultat = controller.creer(etudiant);

        assertEquals("Ngono", resultat.getNom());
        verify(repository).save(etudiant);
    }
}