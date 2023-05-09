package com.example.lojaretrofit.model

data class Produto(

    var descontroPromocao: Double = 0.0,
    var ativoProduto: Boolean = true,
    var precProduto: Double = 0.0,
    var descProduto: String = "",
    var qtdMinEstoque : Int = 0,
    var idProduto : Int = 0,
    var nomeProduto: String = "",
    var idCategoria: Int = 0

)


