package it.mirea.cursov;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adaptery extends RecyclerView.Adapter<Adaptery.MyViewHolder> {

    private Context mContext;
    private List<it.mirea.cursov.CsModelClass> mData;
    private static OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public Adaptery(Context mContext, List<it.mirea.cursov.CsModelClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.course_item, parent, false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mData.get(position).getN_name());
        holder.description.setText(mData.get(position).getD_description());
        holder.genre.setText(mData.get(position).getG_genre());

        Picasso.get()
                .load(mData.get(position).getI_image())
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView description;
        TextView rate;
        TextView genre;
        ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.f_name);
            genre = itemView.findViewById(R.id.f_genre);
            description = itemView.findViewById(R.id.f_description);
            description.setMovementMethod(new ScrollingMovementMethod());
            image = itemView.findViewById(R.id.f_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

}
