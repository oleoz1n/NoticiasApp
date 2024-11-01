package br.com.fiap

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.fiap.databinding.ActivityMainBinding
import br.com.fiap.entities.Post
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private val binding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private val bancoDados by lazy {
        FirebaseFirestore.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
        }

        val btnAddNoticia = binding.btnAddPost

        btnAddNoticia.setOnClickListener {
            startActivity(Intent(this, PostAdd::class.java))
        }
        val recyclerView = binding.postsView
        recyclerView.layoutManager = LinearLayoutManager(this)

        bancoDados.collection("Post")
            .get()
            .addOnSuccessListener { result ->
                val posts = result.toObjects(Post::class.java)
                if (posts.isNotEmpty()) {
                    recyclerView.adapter = (PostAdapter(posts))
                } else {
                    Log.i("posts", "Nenhum post encontrado")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("posts", "Erro ao buscar posts", exception)
            }

        val btnDeslogar = binding.btnLogout
        btnDeslogar.setOnClickListener {
            Log.i("btnDeslogar", "Clicou no botão de deslogar")
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        val btnBuscar = binding.btnBuscar
        btnBuscar.setOnClickListener {
            Log.i("btnBuscar", "Clicou no botão de buscar")

            val txtBuscar = binding.txtFiltro.text.toString()

            if (txtBuscar.isEmpty()) {
                bancoDados.collection("Post")
                    .get()
                    .addOnSuccessListener { result ->
                        val posts = result.toObjects(Post::class.java)
                        if (posts.isNotEmpty()) {
                            recyclerView.adapter = PostAdapter(posts)
                            recyclerView.adapter?.notifyDataSetChanged()
                        } else {
                            Log.i("posts", "Nenhum post encontrado")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("posts", "Erro ao buscar posts", exception)
                    }
                return@setOnClickListener
            }

            if(txtBuscar[0] == '@') {
                bancoDados.collection("Post")
                    .whereEqualTo("autor", txtBuscar.substring(1))
                    .get()
                    .addOnSuccessListener { result ->
                        val posts = result.toObjects(Post::class.java)
                        if (posts.isNotEmpty()) {
                            recyclerView.adapter = PostAdapter(posts)
                            recyclerView.adapter?.notifyDataSetChanged()
                        } else {
                            Log.i("posts", "Nenhum post encontrado")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("posts", "Erro ao buscar posts", exception)
                    }
                return@setOnClickListener
            }

            else if(txtBuscar[0] == '#') {
                bancoDados.collection("Post")
                    .whereEqualTo("tema", txtBuscar.substring(1))
                    .get()
                    .addOnSuccessListener { result ->
                        val posts = result.toObjects(Post::class.java)
                        if (posts.isNotEmpty()) {
                            recyclerView.adapter = PostAdapter(posts)
                            recyclerView.adapter?.notifyDataSetChanged()
                        } else {
                            Log.i("posts", "Nenhum post encontrado")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("posts", "Erro ao buscar posts", exception)
                    }
                return@setOnClickListener
            }

            else {
                bancoDados.collection("Post")
                    .whereEqualTo("titulo", txtBuscar)
                    .get()
                    .addOnSuccessListener { result ->
                        val posts = result.toObjects(Post::class.java)
                        if (posts.isNotEmpty()) {
                            recyclerView.adapter = PostAdapter(posts)
                            recyclerView.adapter?.notifyDataSetChanged()
                        } else {
                            Log.i("posts", "Nenhum post encontrado")
                        }
                    }
                    .addOnFailureListener { exception ->
                        Log.e("posts", "Erro ao buscar posts", exception)
                    }
                return@setOnClickListener
            }
        }
    }

}