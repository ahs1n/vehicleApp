package com.example.vehicleapp.model

import androidx.room.Embedded
import androidx.room.Relation

/**
 * @author AliAzazAlam on 5/10/2021.
 */
data class VehicleAttendance(

    @Embedded val vehicles: VehiclesItem,
    @Relation(
        parentColumn = "vehicleNo",
        entityColumn = "vehicleNo"
    )
    var attendance: Attendance?

)