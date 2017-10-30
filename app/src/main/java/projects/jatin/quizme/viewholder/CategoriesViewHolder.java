package projects.jatin.quizme.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import projects.jatin.quizme.R;
import projects.jatin.quizme.interfaces.ItemClickListener;

/**
 * Created by Jateen on 30-10-2017.
 */

public class CategoriesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView categoryName;
    public ImageView categoryImage;

    private ItemClickListener itemClickListener;

    public CategoriesViewHolder(View itemView) {
        super(itemView);
        categoryImage=itemView.findViewById(R.id.categoryImage);
        categoryName=itemView.findViewById(R.id.categoryName);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {

        itemClickListener.onClick(view,getAdapterPosition(),false);
    }
}
