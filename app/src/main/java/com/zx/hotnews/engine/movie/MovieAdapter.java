package com.zx.hotnews.engine.movie;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.zx.hotnews.R;
import com.zx.hotnews.bean.movie.SubjectsBean;
import com.zx.hotnews.engine.WebViewActivity;

import java.util.ArrayList;

/**
 * Created by zhangxin on 2016/11/26.
 * <p>
 * Description :
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {
    private Activity mContext;
    ArrayList<SubjectsBean> list;

    public MovieAdapter(Activity context, ArrayList<SubjectsBean> list) {
        super();
        mContext = context;
        this.list = list;
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_movie, parent, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(final MovieHolder holder, int position) {
        final SubjectsBean subjectsBean = list.get(position);
        Glide.with(mContext)
                .load(subjectsBean.getImages().getSmall())
                .placeholder(R.drawable.img_default_meizi)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.moviePic);
        holder.movieName.setText(subjectsBean.getTitle());
        holder.movieDirector.setText(subjectsBean.getDirectors().get(0).getName());
        StringBuilder sb = new StringBuilder(30);
        sb.append("主演:");
        for (int i = 0; i < subjectsBean.getCasts().size(); i++) {
            sb.append(subjectsBean.getCasts().get(i).getName() + "/");
        }
        holder.movieActors.setText(sb.toString());
        sb = new StringBuilder(20);
        sb.append("类型");
        for (int i = 0; i < subjectsBean.getGenres().size(); i++) {
            sb.append(subjectsBean.getGenres().get(i) + "/");
        }
        holder.movieType.setText(sb.toString());
        holder.movieRating.setText("评分:" + subjectsBean.getRating().getStars());

        final String url = subjectsBean.getAlt();
        /*holder.movieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra("url", url);
                mContext.startActivity(intent);
            }
        });*/

        holder.itemView.setScaleX(0.8f);
        holder.itemView.setScaleY(0.8f);

//        ViewPropertyAnimator.animate(holder.itemView).scaleX(1).setDuration(350).setInterpolator(new
// OvershootInterpolator()).start();
//        ViewPropertyAnimator.animate(holder.itemView).scaleY(1).setDuration(350).setInterpolator(new
// OvershootInterpolator()).start();

        holder.itemView.animate().scaleX(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();
        ;
        holder.itemView.animate().scaleY(1).setDuration(350).setInterpolator(new OvershootInterpolator()).start();

        holder.movieItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MovieDetailActivity.start(mContext, subjectsBean, holder.moviePic);
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
