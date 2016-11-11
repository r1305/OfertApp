package comp.ofertapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout dl;
    Toolbar toolbar;
    NavigationView nav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        nav = (NavigationView) findViewById(R.id.navigation);
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.animate();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dl.openDrawer(GravityCompat.START);
                if (dl.isDrawerOpen(GravityCompat.START)) {
                    dl.closeDrawers();
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, dl, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        dl.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment card=CardFragment.newInstance();
        ft.replace(R.id.flaContenido,card);
        ft.addToBackStack(null);
        toolbar.setTitle("Perfil");
        ft.commit();

        nav.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        switch (item.getItemId()) {
            case R.id.logout:
                //session.logoutUser();
                //LoginManager.getInstance().logOut();
                return true;
            case R.id.mapa:
                Intent i = new Intent(InicioActivity.this, MapsActivity.class);
                startActivity(i);
                return true;
            case R.id.perfil:
                //Fragment perfil=PerfilFragment.newInstance(datos);
                //ft.replace(R.id.flaContenido,perfil);
                toolbar.setTitle("Perfil");
                //ft.commit();
                dl.closeDrawers();
                Toast.makeText(InicioActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                return true;


        }
        return false;
    }
}
