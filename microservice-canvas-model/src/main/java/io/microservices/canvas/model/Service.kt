package io.microservices.canvas.model

data class Service(val name: String, val description: String,
                   val capabilities : List<String>, val api: Api,
                   val dependencies: Dependencies)
