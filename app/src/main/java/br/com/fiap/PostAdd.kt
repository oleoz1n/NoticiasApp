package br.com.fiap

import android.graphics.Color.rgb
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.fiap.databinding.ActivityPostAddBinding
import br.com.fiap.entities.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Locale

class PostAdd : AppCompatActivity() {

    private val binding by lazy{
        ActivityPostAddBinding.inflate(layoutInflater)
    }

    private val bancoDados by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.post_add)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnVoltarAddPost.setOnClickListener {
            finish()
        }

        val txtTitulo = binding.txtTitulo
        val txtDesc = binding.txtDesc
        val spinnerTema = binding.spinnerTema
        val textResposta = binding.respostaAddPost

        txtTitulo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        txtDesc.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        val temas = arrayOf("Tecnologia", "Esportes", "Entretenimento", "Política", "Economia")
        spinnerTema.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, temas)

        val btnEnviar = binding.btnEnviar

        btnEnviar.setOnClickListener {
            if(txtTitulo.text!!.isEmpty() || txtDesc.text!!.isEmpty()){
                textResposta.text = "Preencha todos os campos"
                textResposta.visibility = View.VISIBLE
                return@setOnClickListener
            }


            val dataGeracao = System.currentTimeMillis().let {
                val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                formatter.format(it)
            }
            val titulo = txtTitulo.text.toString()
            val desc = txtDesc.text.toString()
            val tema = spinnerTema.selectedItem.toString()
            val autor = auth.currentUser?.email?: "Anônimo"


        val post = Post(titulo, desc, dataGeracao, tema, autor)
        bancoDados.collection("Post")
            .add(post)
            .addOnSuccessListener {
                textResposta.setTextColor(rgb(0,255,0))
                txtTitulo.text!!.clear()
                txtDesc.text!!.clear()
                binding.btnEnviar.postDelayed({
                    finish()
                }, 1000)
            }
            .addOnFailureListener {
                textResposta.text = "Erro ao enviar post"
                textResposta.visibility = View.VISIBLE
            }
        }
    }
}