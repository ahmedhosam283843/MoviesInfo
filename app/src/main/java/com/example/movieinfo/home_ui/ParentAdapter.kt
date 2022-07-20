import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieinfo.R
import com.example.movieinfo.api.MoviesResponse
import com.example.movieinfo.home_ui.ChildItem
import com.example.movieinfo.home_ui.ParentItem
import com.example.movieinfo.home_ui.ChildAdapter
import kotlinx.android.synthetic.main.home_item_1.view.*


class ParentAdapter(var parentalModelList: List<ParentItem>, var response: MoviesResponse?) :
    RecyclerView.Adapter<ParentAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var category: TextView = itemView.home1_textView
        var childRecyclerView: RecyclerView = itemView.home2_rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.home_item_1,
                parent,
                false
            )
        )
    }



    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        var currentItem = parentalModelList[position]
        var layoutManager = LinearLayoutManager(
            holder.childRecyclerView.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        holder.childRecyclerView.layoutManager = layoutManager
        holder.childRecyclerView.setHasFixedSize(true)
        holder.category.text = currentItem.movieCategory
        holder.childRecyclerView.adapter = ChildAdapter(response!!.results)
    }

    override fun getItemCount(): Int {
        return parentalModelList.size
    }
}