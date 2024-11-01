package br.com.fiap

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fiap.databinding.FragmentLogarBinding
import com.google.firebase.auth.FirebaseAuth

class LogarFragment(auth: FirebaseAuth) : Fragment() {

    private var _binding: FragmentLogarBinding? = null
    private val binding get() = _binding!!

    val auth = auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLogarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txtEmail = binding.txtEmail
        val txtSenha = binding.txtPassword
        val textResposta = binding.resposta
        val btnLogin = binding.btnLogar

        txtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        txtSenha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnLogin.setOnClickListener {

            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()
            Log.i("LogarFragment", "Email: $email, Senha: $senha")

            if (email.equals("")|| senha.equals("")) {
                //resposta = "Preencha todos os campos"
                textResposta.text = "Preencha todos os campos"
                textResposta.visibility = View.VISIBLE
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, senha)
                .addOnSuccessListener { authResult ->
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                .addOnFailureListener { e ->
                    if(e.message == "The supplied auth credential is incorrect, malformed or has expired."){
                        textResposta.text = "Email ou senha incorretos"
                    } else {
                        Log.i("LogarFragment", "Erro ao logar: ${e.message}")
                        textResposta.text = "Erro ao logar, tente novamente"
                    }
                    textResposta.visibility = View.VISIBLE
                }
        }
        super.onViewCreated(view, savedInstanceState)
    }

     override fun onDestroyView() {
         _binding = null
         super.onDestroyView()
    }

}