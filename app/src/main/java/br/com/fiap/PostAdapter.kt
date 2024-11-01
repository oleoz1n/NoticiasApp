package br.com.fiap

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.fiap.entities.Post

class PostAdapter(var posts: List<Post>) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val titulo = itemView.findViewById<TextView>(R.id.titulo)
        val dataGeracao = itemView.findViewById<TextView>(R.id.dataGeracao)
        val tema = itemView.findViewById<TextView>(R.id.tema)
        val autor = itemView.findViewById<TextView>(R.id.autor)
        val itemContainer: LinearLayout = itemView.findViewById(R.id.itemContainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val autor = "Autor: " + posts[position].autor

        holder.titulo.text = posts[position].titulo
        holder.dataGeracao.text = posts[position].dataGeracao
        holder.tema.text = posts[position].tema
        holder.autor.text = autor

        holder.itemContainer.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, PostDetails::class.java)
            intent.putExtra("post", posts[position])
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return posts.size
    }

}