package comp.ofertapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterActivity extends AppCompatActivity {

    Spinner sexo,ocupacion,distrito;
    EditText fec_nac;
    Button reg;
    CircleImageView img;
    String urlImg;
    int PICK_IMAGE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        sexo=(Spinner)findViewById(R.id.sign_sexo);
        ocupacion=(Spinner)findViewById(R.id.sign_ocupacion);
        distrito=(Spinner)findViewById(R.id.sign_distrito);
        fec_nac=(EditText)findViewById(R.id.sign_fecha_nac);
        img=(CircleImageView)findViewById(R.id.sign_img);
        reg=(Button)findViewById(R.id.sign_btn);

        TextWatcher tw=new TextWatcher() {
            private String current = "";
            private String ddmmyyyy = "DDMMYYYY";
            private Calendar cal = Calendar.getInstance();
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equals(current)) {
                    String clean = s.toString().replaceAll("[^\\d.]", "");
                    String cleanC = current.replaceAll("[^\\d.]", "");

                    int cl = clean.length();
                    int sel = cl;
                    for (int i = 2; i <= cl && i < 6; i += 2) {
                        sel++;
                    }
                    //Fix for pressing delete next to a forward slash
                    if (clean.equals(cleanC)) sel--;

                    if (clean.length() < 8){
                        clean = clean + ddmmyyyy.substring(clean.length());
                    }else{
                        //This part makes sure that when we finish entering numbers
                        //the date is correct, fixing it otherwise
                        int day  = Integer.parseInt(clean.substring(0,2));
                        int mon  = Integer.parseInt(clean.substring(2,4));
                        int year = Integer.parseInt(clean.substring(4,8));

                        if(mon > 12) mon = 12;
                        cal.set(Calendar.MONTH, mon-1);
                        year = (year<1900)?1900:(year>2100)?2100:year;
                        cal.set(Calendar.YEAR, year);
                        // ^ first set year for the line below to work correctly
                        //with leap years - otherwise, date e.g. 29/02/2012
                        //would be automatically corrected to 28/02/2012

                        day = (day > cal.getActualMaximum(Calendar.DATE))? cal.getActualMaximum(Calendar.DATE):day;
                        clean = String.format("%02d%02d%02d",day, mon, year);
                    }

                    clean = String.format("%s/%s/%s", clean.substring(0, 2),
                            clean.substring(2, 4),
                            clean.substring(4, 8));

                    sel = sel < 0 ? 0 : sel;
                    current = clean;
                    fec_nac.setText(current);
                    fec_nac.setSelection(sel < current.length() ? sel : current.length());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        fec_nac.addTextChangedListener(tw);

        List<String> gender=new ArrayList<>();
        gender.add("Masculino");
        gender.add("Femenino");
        ArrayAdapter<String> sexoAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, gender);
        sexoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexo.setAdapter(sexoAdapter);

        List<String> ocu=new ArrayList<>();
        ocu.add("Estudiante");
        ocu.add("Independiente");
        ocu.add("Dependiente");
        ArrayAdapter<String> ocuAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ocu);
        ocuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ocupacion.setAdapter(ocuAdapter);

        List<String> dist=new ArrayList<>();
        dist.add("Ancón");
        dist.add("Ate");
        dist.add("Barranco");
        dist.add("Bellavista");
        dist.add("Breña");
        dist.add("Callao");
        dist.add("Carabayllo");
        dist.add("Carmen de la Legua");
        dist.add("Chaclacayo");
        dist.add("Chorrillos");
        dist.add("Cieneguilla");
        dist.add("Comas");
        dist.add("El Agustino");
        dist.add("Independencia");
        dist.add("Jesús María");
        dist.add("La Molina");
        dist.add("La Perla");
        dist.add("La Punta");
        dist.add("La Victoria");
        dist.add("La Molina");
        dist.add("Lima");
        dist.add("Lince");
        dist.add("Los Olivos");
        dist.add("Lurigancho");
        dist.add("Lurín");
        dist.add("Magdalena del Mar");
        dist.add("Magdalena vieja");
        dist.add("Miraflores");
        dist.add("Pachacamac");
        dist.add("Pucusana");
        dist.add("Puente Piedra");
        dist.add("Punta Hermosa");
        dist.add("Punta Negra");
        dist.add("Rímac");
        dist.add("San Bartolo");
        dist.add("San Borja");
        dist.add("San Isidro");
        dist.add("San Juan de Miraflores");
        dist.add("San Martín de Porres");
        dist.add("San Miguel");
        dist.add("Santa Anita");
        dist.add("Santa María del Mar");
        dist.add("Santa Rosa");
        dist.add("Santiago de Surco");
        dist.add("Surquillo");
        dist.add("Ventanilla");
        dist.add("Villa el Salvador");
        dist.add("Villa María del Triunfo");
        ArrayAdapter<String> distAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, dist);
        distAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        distrito.setAdapter(distAdapter);

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,InicioActivity.class);
                startActivity(i);
                RegisterActivity.this.finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            try {

                System.out.println("******* " + data.getData());
                Uri imageUri = data.getData();
                img.setImageURI(imageUri);
                Picasso.with(RegisterActivity.this).load(imageUri).into(img);
                //img.setVisibility(View.VISIBLE);
                //writePhoto(img);

            } catch (Exception e) {
                System.out.println("*** error: " + e);
            }
        }
    }

    private void openGallery() {
        Intent gallery =
                new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    public void writePhoto(ImageView img) {

        BitmapDrawable bm = (BitmapDrawable) img.getDrawable();
        Bitmap mysharebmp = bm.getBitmap();

        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            mysharebmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

            //you can create a new file name "test.jpeg"
            File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    + File.separator + "tmp.jpeg");

            if (f.exists()) {
                f.delete();
                f.createNewFile();
            } else {
                f.createNewFile();
            }

            //write the bytes in file
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

            // remember close de FileOutput
            fo.close();
            urlImg = f.getPath();
            System.out.println("**** " + f.getPath());
            Picasso.with(RegisterActivity.this).load(urlImg).into(img);
            //new Upload().execute();

            //Toast.makeText(SignUpActivity.this, urlImg, Toast.LENGTH_SHORT).show();
            //uploadPhoto(urlImg);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
