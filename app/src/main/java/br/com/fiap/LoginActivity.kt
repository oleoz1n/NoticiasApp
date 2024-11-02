package br.com.fiap

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import br.com.fiap.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val auth by lazy {
        FirebaseAuth.getInstance()
    }

    private var loginView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        hideButtons()

        binding.btnLogin.setOnClickListener {
            loginView = true
            hideButtons()
            val fragment = LogarFragment(auth)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
        }

        binding.btnCadastro.setOnClickListener {
            loginView = false
            hideButtons()
            val fragment = CadastroFragment(auth)
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
        }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        val fragment = LogarFragment(auth)
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }

    private fun hideButtons(){
        if (loginView){
            binding.btnCadastro.visibility = View.VISIBLE
            binding.btnLogin.visibility = View.GONE
        } else {
            binding.btnCadastro.visibility = View.GONE
            binding.btnLogin.visibility = View.VISIBLE
        }
    }

}