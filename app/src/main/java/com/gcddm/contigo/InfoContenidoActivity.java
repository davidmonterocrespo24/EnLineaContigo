package com.gcddm.contigo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bluejamesbond.text.DocumentView;
import com.gcddm.contigo.Util.ImagenAdapter;
import com.gcddm.contigo.db.Imagen;
import com.gcddm.contigo.db.Info;
import com.gcddm.contigo.db.Juego;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class InfoContenidoActivity extends AppCompatActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Info info;
    public List<Imagen> imagenes;


    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_contenido);

        System.gc();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupActionBar();

        long id = getIntent().getLongExtra("id", 0);


        //Aqui se agregan los tabs
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(TextoFragment.newInstance(id), "Información");
        mSectionsPagerAdapter.addFragment(ImagenFragment.newInstance(id), "Galería");
        mSectionsPagerAdapter.addFragment(VideoFragment.newInstance(id), "Video");


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Información");
        tabOne.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tabOne.setTextColor(Color.WHITE);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Galería");
        tabTwo.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tabTwo.setTextColor(Color.WHITE);
        tabLayout.getTabAt(1).setCustomView(tabTwo);


        TextView tabTree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTree.setText("Video");
        tabTree.setGravity(View.TEXT_ALIGNMENT_CENTER);
        tabTree.setTextColor(Color.WHITE);
        tabLayout.getTabAt(2).setCustomView(tabTree);

    }
//Contiene el texto de la informacion
    public static class TextoFragment extends Fragment {
        private static final String ARG_ID = "TEXTO";
        private long id;
        private String texto;

        public TextoFragment() {
        }

        public static TextoFragment newInstance(long id) {
            TextoFragment fragment = new TextoFragment();
            Bundle args = new Bundle();
            args.putLong(ARG_ID, id);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            Bundle args = getArguments();

            if (args != null){
                id = args.getLong(ARG_ID);
                Info i = Info.findById(Info.class, id);

                if (i != null){
                    texto = i.getTexto_info();
                } else {
                    texto ="No se encontro un texto";
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_texto, container, false);
            DocumentView textView = (DocumentView) rootView.findViewById(R.id.texto_inform);
            textView.setText(texto);

            return rootView;
        }
    }

 //Contiene las imagenes
    public static class ImagenFragment extends Fragment {
        private long mSubtemaId;
        private List<Imagen> imagenes = new ArrayList();

        public ImagenFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static ImagenFragment newInstance(long temaId) {
            Bundle args = new Bundle();
            args.putLong("tema_id", temaId);


            ImagenFragment fragment = new ImagenFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();

            if (args != null){
                mSubtemaId = args.getLong("tema_id");
                Info info = Info.findById(Info.class,mSubtemaId);
                List<Imagen> list = info.getImagenes();

                for (Imagen i : list){
                    imagenes.add(i);
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_info_imagenes, container, false);
            GridView gridView = (GridView) rootView.findViewById(R.id.imagen_info_GridView);
            gridView.setAdapter(new ImagenAdapter(getContext(), imagenes));
            gridView.setEmptyView(rootView.findViewById(R.id.emptyGallery));
            return rootView;
        }
    }

    //Contiene los videos
    public static class VideoFragment extends Fragment {
        private long mTemaId;

        VideoView videoView;
        File videoFile = null;
        boolean pause = false;

        public VideoFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static VideoFragment newInstance(long temaId) {
            Bundle args = new Bundle();
            args.putLong("tema_id", temaId);


            VideoFragment fragment = new VideoFragment();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();

            if (args != null){
                mTemaId = args.getLong("tema_id");
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_info_video, container, false);
            videoView = (VideoView) rootView.findViewById(R.id.video_view);
            final Button btnPlay = (Button) rootView.findViewById(R.id.buttonPlay);
            View view = (View) rootView.findViewById(R.id.view);
            TextView text_Empty = (TextView)rootView.findViewById(R.id.emptyMovie);

            Info info = Info.findById(Info.class, mTemaId);

            if (info != null && info.getVideo() != null){
                videoFile = new File(info.getVideo());
            } else {
                view.setVisibility(View.GONE);
                text_Empty.setVisibility(View.VISIBLE);
            }

            btnPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (videoFile != null && videoFile.exists()){
                        videoView.setVideoPath(videoFile.getPath());

                        if (!videoView.isPlaying()){
                            videoView.start();
                            btnPlay.setVisibility(View.INVISIBLE);
                            videoView.setMediaController(new MediaController(getContext()));
                        }

                    }
                }
            });

            return rootView;
        }
    }



    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ic_menu_general, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.home:
                // TODO put your code here to respond to the button tap
                Intent i = new Intent(InfoContenidoActivity.this, InicioActivity.class);
                startActivity(i);
                InfoContenidoActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Set up the {@link android.app.ActionBar}, if the API is available.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Hide the action bar title

        }
    }
}
