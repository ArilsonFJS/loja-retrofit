package com.example.lojaretrofit.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.lojaretrofit.api.API
import com.example.lojaretrofit.databinding.ActivityListaProdutosBinding
import com.example.lojaretrofit.databinding.CardItemBinding
import com.example.lojaretrofit.model.Produto
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaProdutosActivity : AppCompatActivity() {

    lateinit var binding: ActivityListaProdutosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    //executa toda vez que a tela aparecer
    override fun onResume() {
        super.onResume()

        listarProdutos()
    }

    fun listarProdutos(){

        val callback = object : Callback <List<Produto>> {
            override fun onResponse(call: Call<List<Produto>>, response: Response<List<Produto>>) {
                if(response.isSuccessful){
                    val listaProdutos = response.body()
                    if(listaProdutos != null){
                        atualizarTela(listaProdutos)
                    }
                }else{
                    Snackbar.make(binding.root, "Impossivel obter lista de produtos", Snackbar.LENGTH_LONG).show()
                    val erroBody = response.errorBody()
                    if(erroBody != null){
                        Log.e("ListaProdutoActivity",erroBody.string())
                    }

                }
                binding.progressBar.visibility = View.INVISIBLE
            }

            override fun onFailure(call: Call<List<Produto>>, t: Throwable) {
                Snackbar.make(binding.root, "Não foi possível se conectar ao servidor", Snackbar.LENGTH_LONG).show()

                Log.e("ListaProdutoActivity","ListarProdutos",t)
                binding.progressBar.visibility = View.INVISIBLE
            }

        }

        API.produto.listar().enqueue(callback)
        binding.progressBar.visibility = View.VISIBLE
    }

    fun atualizarTela(ListaProdutos: List<Produto>){
        //limpar o container
        binding.container.removeAllViews()

        //iterar a lista
        ListaProdutos.forEach {
        //instancioar o cartao para cada item da lista
            val cardBinding = CardItemBinding.inflate(layoutInflater)
            cardBinding.nome.text = it.nomeProduto
            cardBinding.preco.text = it.precProduto.toString()

            Picasso
                .get()
                .load("https://oficinacordova.azurewebsites.net/android/rest/produto/image/")
                .into(cardBinding.imagem)

            //adicionar a instancia do cartao dentro do container
            binding.container.addView(cardBinding.root)
        }
    }
}