package com.example.scoreup.features.home.data.repositories

import com.example.scoreup.features.home.data.datasources.remote.api.HomeApi
import com.example.scoreup.features.home.data.datasources.remote.mapper.toDomain
import com.example.scoreup.features.home.domain.entities.Challenge
import com.example.scoreup.features.home.domain.repositories.ChallengeRepository
import javax.inject.Inject

class ChallengeRepositoryImpl @Inject constructor(
    private val homeApi: HomeApi
) : ChallengeRepository {

    override suspend fun getAllChallenges(): List<Challenge> {
        // Comentado temporalmente hasta que la API esté lista
        /*
        val response = homeApi.getChallenges()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.map { it.toDomain() }
        } else {
            throw Exception("Error al obtener los retos")
        }
        */

        // Datos de prueba (Hardcoded)
        return listOf(
            Challenge(
                idReto = 1,
                idUsuario = 101,
                materia = "Matemáticas",
                descripcion = "Resolver 20 ejercicios de cálculos integral",
                meta = "5",
                puntosOtorgados = 50,
                fechaLimite = "13 días restantes",
                fechaCreacion = "2024-02-20"
            ),
            Challenge(
                idReto = 2,
                idUsuario = 102,
                materia = "Física",
                descripcion = "Completar laboratorio de cinemática",
                meta = "1",
                puntosOtorgados = 30,
                fechaLimite = "5 días restantes",
                fechaCreacion = "2024-02-21"
            ),
            Challenge(
                idReto = 3,
                idUsuario = 103,
                materia = "Programación",
                descripcion = "Implementar 3 algoritmos de ordenamiento",
                meta = "3",
                puntosOtorgados = 100,
                fechaLimite = "2 días restantes",
                fechaCreacion = "2024-02-22"
            ),
            Challenge(
                idReto = 4,
                idUsuario = 104,
                materia = "Historia",
                descripcion = "Ensayo sobre la revolución industrial",
                meta = "1",
                puntosOtorgados = 20,
                fechaLimite = "Completado",
                fechaCreacion = "2024-02-15"
            )
        )
    }
}
