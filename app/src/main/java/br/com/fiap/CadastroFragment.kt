package br.com.fiap

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.fiap.databinding.FragmentCadastroBinding
import com.google.firebase.auth.FirebaseAuth


class CadastroFragment(auth: FirebaseAuth) : Fragment() {
    private var _binding: FragmentCadastroBinding? = null
    private val binding get() = _binding!!

    val auth = auth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCadastroBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val txtEmail = binding.txtEmail
        val txtSenha = binding.txtPassword
        val txtSenhaConfirm = binding.txtPasswordConfirm
        val textResposta = binding.resposta
        val btnCadastro = binding.btnCadastrar
        val erroEmail = binding.erroEmail
        val erroPassword = binding.erroPassword
        val erroPasswordConfirm = binding.erroPasswordConfirm

        txtEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                erroEmail.visibility = View.GONE
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        txtSenha.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                erroPassword.visibility = View.GONE
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        txtSenhaConfirm.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                erroPasswordConfirm.visibility = View.GONE
                textResposta.visibility = View.GONE
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        btnCadastro.setOnClickListener {
            val email = txtEmail.text.toString()
            val senha = txtSenha.text.toString()
            val senhaConfirm = txtSenhaConfirm.text.toString()

            if (email.equals("") || senha.equals("")) {
                textResposta.text = "Preencha todos os campos"
                textResposta.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val regexEmail = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
            if (!regexEmail.matches(email)) {
                erroEmail.text = "Email inválido"
                erroEmail.visibility = View.VISIBLE
                return@setOnClickListener
            }

            val regexSenha =
                Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$")
            if (!regexSenha.matches(senha)) {
                erroPassword.text =
                    "Senha inválida \nA senha deve conter ao menos 8 caracteres\nUma letra maiúscula\nUma letra minúscula\nUm número\nUm caractere especial"
                erroPassword.visibility = View.VISIBLE
                return@setOnClickListener
            }

            if (senha != senhaConfirm) {
                erroPasswordConfirm.text = "Senhas não conferem"
                erroPasswordConfirm.visibility = View.VISIBLE
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, senha)
                .addOnSuccessListener { authResult ->
                    startActivity(Intent(requireContext(), MainActivity::class.java))
                    requireActivity().finish()
                }
                .addOnFailureListener { e ->
                    if (e.message == "The email address is already in use by another account.") {
                        textResposta.text = "Email já cadastrado"
                    }
                    else {
                        textResposta.text = "Erro ao cadastrar, tente novamente"
                    }
                    textResposta.visibility = View.VISIBLE
                }

            super.onViewCreated(view, savedInstanceState)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
