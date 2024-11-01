package br.com.fiap

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.fiap.databinding.ActivityPostDetailsBinding
import br.com.fiap.entities.Post

class PostDetails : AppCompatActivity() {

    private val binding by lazy{
        ActivityPostDetailsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.post_details)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val post: Post? = intent.getParcelableExtra<Post>("post")

        if (post == null) {
            finish()
            return
        }

        val autor = "Autor: " + post.autor

        binding.tituloDetails.text = post.titulo
        binding.dataGeracaoDetails.text = post.dataGeracao
        binding.temaDetails.text = post.tema
        binding.descricaoDetails.text = post.desc
        binding.autorDetails.text = autor

        binding.btnVoltarDetails.setOnClickListener {
            Log.i("PostDetails", "Botão voltar clicado")
            finish()
        }
    }
}