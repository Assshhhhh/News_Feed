package com.example.newsfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.newsfeed.AdapterClass.NewsAdapter;
import com.example.newsfeed.databinding.ActivityMainBinding;
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;
    private String apiKey = "933a6c4dc47a4ea2b386cdabd2832d79";
    private List<Article> articlesList;
    private NewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        articlesList = new ArrayList<>();

        setRecycler();
        getNews("General", null);

        binding.buttonGeneral.setOnClickListener(this);
        binding.buttonBusiness.setOnClickListener(this);
        binding.buttonSports.setOnClickListener(this);
        binding.buttonScience.setOnClickListener(this);
        binding.buttonHealth.setOnClickListener(this);
        binding.buttonTech.setOnClickListener(this);
        binding.buttonEnter.setOnClickListener(this);

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getNews("General", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void setRecycler(){

        binding.recyclerNews.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NewsAdapter(this, articlesList);
        binding.recyclerNews.setAdapter(adapter);

    }

    private void showProgress(boolean show){
        if(show)
            binding.progressLinear.setVisibility(View.VISIBLE);
        else
            binding.progressLinear.setVisibility(View.INVISIBLE);
    }
    private void getNews(String category, String query){

        showProgress(true);

        NewsApiClient newsApiClient = new NewsApiClient(apiKey);

        newsApiClient.getTopHeadlines(
                new TopHeadlinesRequest.Builder()
                        .language("en")
                        .category(category)
                        .q(query)
                        .build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {

                        runOnUiThread(() ->{
                            showProgress(false);
                            articlesList = response.getArticles();
                            adapter.updateData(articlesList);
                            adapter.notifyDataSetChanged();
                        });
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.i("FAILURE", throwable.toString());
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        String category = button.getText().toString();
        getNews(category, null);
    }
}