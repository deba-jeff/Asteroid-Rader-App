package ukponahiunsijeffery.example.asteroidraderapp.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ukponahiunsijeffery.example.asteroidraderapp.databinding.ListItemBinding
import ukponahiunsijeffery.example.asteroidraderapp.domain.AsteroidDomain

class AsteroidAdapter(private val onClickListener: OnClickListener)
    : ListAdapter<AsteroidDomain, AsteroidAdapter.AsteroidDomainViewHolder>(DiffCallback){


    class AsteroidDomainViewHolder(private var binding: ListItemBinding) :
        RecyclerView.ViewHolder(binding.root){

            fun bind(asteroidDomain: AsteroidDomain, clickListener: OnClickListener){
                binding.asteroidDomain = asteroidDomain
                binding.clicklistener = clickListener
                binding.executePendingBindings()
            }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<AsteroidDomain>(){
        override fun areItemsTheSame(oldItem: AsteroidDomain, newItem: AsteroidDomain): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: AsteroidDomain, newItem: AsteroidDomain): Boolean {
            return oldItem.id == newItem.id
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidDomainViewHolder {
        return AsteroidDomainViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }


    override fun onBindViewHolder(holder: AsteroidDomainViewHolder, position: Int) {
        val asteroidDomain = getItem(position)
        holder.bind(asteroidDomain, onClickListener)
    }


    class OnClickListener(val clickListener: (asteroidDomain: AsteroidDomain) -> Unit){
        fun onClick(asteroidDomain: AsteroidDomain) {
            clickListener(asteroidDomain)
        }
    }


}

