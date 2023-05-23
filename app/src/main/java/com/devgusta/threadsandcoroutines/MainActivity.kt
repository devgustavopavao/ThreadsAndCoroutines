package com.devgusta.threadsandcoroutines

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.devgusta.threadsandcoroutines.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var job: Job? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.btnNovatela.setOnClickListener {
            startActivity(
                Intent(this, NovaTela::class.java)
            )
            finish()
        }
        binding.btnIniciar.setOnClickListener {
            // MinhaThread().start()
            //usando Coroutine
//          job = CoroutineScope(Dispatchers.IO).launch {
//
//            val tempo = measureTimeMillis {
//                val task1 = async {
//                    tarefa1()
//                }
//                val task2 = async {
//                    tarefa2()
//                }
//
//                Log.i("info_teste", "task1: ${task1.await()}")
//                Log.i("info_teste", "task2: ${task2.await()}")
//            }
//              Log.i("info_teste", "Tempo: $tempo")
//          }

            lifecycleScope.launch {
                repeat(15) { i ->
                    Log.i("info_teste", "Executando > $i")
                    delay(1000L)
                }

            }

        }
        binding.btnParar.setOnClickListener {
            job?.cancel()
            binding.btnIniciar.text = "Parou"
            binding.btnIniciar.isEnabled = true
        }

    }

    @SuppressLint("SetTextI18n")
    private suspend fun executar() {
        repeat(15) { i ->

            Log.i("info_teste", "Executando > $i")
            withContext(Dispatchers.Main) {
                binding.btnIniciar.text = "Executando: $i"
                binding.btnIniciar.isEnabled = false
                if (i == 14) {
                    binding.btnIniciar.text = "Finalizou"
                    binding.btnIniciar.isEnabled = true
                }
            }
            delay(1000L)
        }

//        val user = carregarDados()
//        Log.i("info_teste", "usuario: ${user.nome} ")
//        val idpostagem = recuperarID(user.id)
//        Log.i("info_teste", "postagem: ${idpostagem.size} ")

    }

    private suspend fun tarefa1(): String {
        repeat(5) { i ->
            Log.i("info_teste", "Executando > $i")
        }
        delay(1000L)
        return "Executou task 1"
    }

    private suspend fun tarefa2(): String {
        repeat(5) { i ->
            Log.i("info_teste", "Executando > $i")
        }
        delay(1000L)
        return "Executou task 2"
    }


    private suspend fun carregarDados(): User {
        delay(2000)
        return User("1", "Gustavo Pavao")
    }

    private suspend fun recuperarID(id: String): List<String> {
        delay(2000)
        return listOf(
            "Jogando bola com os parsa",
            "Comendo a gordinha",
            "Jogando no meu ps5"
        )
    }

    inner class MinhaThread() : Thread() {

        @SuppressLint("SetTextI18n")
        override fun run() {
            super.run()

            repeat(15) { i ->
                Log.i("info_teste", "Executando > $i")
                runOnUiThread {
                    binding.btnIniciar.text = "Executando: $i ${currentThread()}"
                    binding.btnIniciar.isEnabled = false
                    if (i == 14) {
                        binding.btnIniciar.text = "Finalizou"
                        binding.btnIniciar.isEnabled = true
                    }
                }
                sleep(1000)
            }
        }
    }
}