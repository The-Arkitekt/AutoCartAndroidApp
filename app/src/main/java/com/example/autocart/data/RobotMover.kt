package com.example.autocart.data

import com.example.autocart.model.RobotMovement
import com.example.autocart.network.AutocartApiService
import okhttp3.ResponseBody
import retrofit2.Response

/**
Handles communication with RobotMover service
 */
interface RobotMover {
    // sends command to robot mover
    suspend fun postMoveCommand(robotMovement: RobotMovement): Response<ResponseBody>
}

/** Network implementation */
class NetworkRobotMover(
    private val autoCartAPIService: AutocartApiService
) : RobotMover {
    /** sends command to robot mover */
    override suspend fun postMoveCommand(robotMovement: RobotMovement): Response<ResponseBody>
        = autoCartAPIService.postMoveCommand(robotMovement.magnitude, robotMovement.direction)
}