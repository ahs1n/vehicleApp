package com.example.vehicleapp.api

import com.example.vehicleapp.BuildConfig
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody

const val SUCCESS_CODE = 200

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(BuildConfig.FLAVOR == "dev" && BuildConfig.BUILD_TYPE == "debug"){
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.contains("api/users") -> userResponse
                uri.contains("api?table=view_vehicles") -> vehicleResponse
                else -> ""
            }
            return chain.proceed(chain.request())
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        }else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with devStaging mode"
            )
        }
    }
}

const val userResponse = """
[
    {
        "id": "1",
        "username": "test1234",
        "password": "test1234",
        "full_name": "Ali",
        "location": "1"
    },
    {
        "id": "2",
        "username": "test4321",
        "password": "test4321",
        "full_name": "Wasim",
        "location": "1"
    },
    {
        "id": "3",
        "username": "sameer",
        "password": "abcd1234",
        "full_name": "Sameer Hanif",
        "location": "1"
    },
    {
        "id": "4",
        "username": "sheeraz",
        "password": "abcd1234",
        "full_name": "Sheeraz Ali Nawaz",
        "location": "4"
    }
]
"""

const val vehicleResponse= """
[
  {
    "vehicleNo": "CT-6963",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-4090",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-7983",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CT-9769",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-9236",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1965",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-9941",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2627",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1171",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1125",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BUN-662 ",
    "model": "Yaris",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-0122",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BQJ-542",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2561 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7065",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-6219",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-2594",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CT-4365",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-3088",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-9430",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2203",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6574",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6679",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1242",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-6355",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1685",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6781",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-2416",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-7932",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-8767",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-1847",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-3739",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CT-6697",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1622",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-5521",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5438",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7446",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-7619",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7756",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-2290",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5509",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-0642",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-9483",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BRJ-482",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-7818",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-1598",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1063",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-0538",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-4977",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-0677",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-5434",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-9366",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-3829",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-0469",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4291",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-5344",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-4064",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-0072",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-6324",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-7040",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-8003",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-9303",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-3265",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-3008",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2987",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-6022 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5335",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-6736",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-4528",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-0517 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-8761 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-1420 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-7332",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": " CW-2610 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": " CY-6898 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-2308",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": " CW-4027",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-5800",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-7303 ",
    "model": "Hijet",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BGH-911",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-6765",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BQW-813 ",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-0125",
    "model": "Hijet",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-7660",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BQE-340",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7206",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BPE-479 ",
    "model": "Cultus",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-8639 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-0363 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-9038 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BGB-647 ",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-6109 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-3746",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-0658",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1524",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1708 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4193",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7814",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BLQ-477",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-9536",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-1358",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-1749",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2813",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1590",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-0213",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-3497",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-2426 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BQV-790 ",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-6155 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-9827 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1003",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-5409",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-3201",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2729",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-0277 ",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-1599",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-5948",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-8763",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-0964",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6240",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-4456",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-3435",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-0252",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-0545",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7564",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-6274",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-3595",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2871",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-0650",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-5974",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-1194",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-8026",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-4348",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5788",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-7615",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-1864",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-8441",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-9048",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-0816",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1612",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-0819",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "LA-4160",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-6984",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-8212",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-8812",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7138",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-0617",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1129",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5792",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7875",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-4697",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7070",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7487",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6196",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-6201",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4159",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-1807",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-7250",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-8562",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-2314",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-6850",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2807",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "LD-1133",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-3092",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-4235",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-2110",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-6014",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2554",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-2186",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-5846",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2333",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-7902",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-9441",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-1727",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-2976",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-3419",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4263",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "LD-0510",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-2723",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-8537",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-1715",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-4990",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-2586",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-8151",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-6843",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-0692 /",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1936",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-8460",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1118",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-8469",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-3885",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6415",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-0761",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-0943",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-5906",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-5605",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "LB-1704",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-5398",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-0276",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-0658",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-4727",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-4716",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-8074",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BG-6919",
    "model": "Fortuner",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-2392",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BH-2650",
    "model": "Fortuner",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "KW-8711",
    "model": "Hiace",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-6605",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-9446",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "KS-6899",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-3409",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-0057",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-1057",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-9950",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4686",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-4961",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-3592",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-1632",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-6554",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CX-1411",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-9322",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-6195",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BSK-872",
    "model": "Cultus",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-4067",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BPG-160",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CU-0348",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BSZ-769",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BPW-272",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CV-7820",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BLN-950",
    "model": "Corolla",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BEU-361",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-9208",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CY-0282",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BLB-257",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "BCJ-547",
    "model": "Wagon R",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-1226",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-3602",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CZ-1653",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-1275",
    "model": "Changan Alsvin",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-2024",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-7381",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "SA-0839",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  },
  {
    "vehicleNo": "CW-9671",
    "model": "Bolan",
    "location": "Karachi",
    "location_id": 1
  }
]
"""