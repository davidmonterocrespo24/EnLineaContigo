package com.gcddm.contigo;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.RelativeLayout;


import com.gcddm.contigo.db.Configuracion;
import com.gcddm.contigo.db.Frases;
import com.gcddm.contigo.db.SubTema;
import com.gcddm.contigo.db.TemaCaliente;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    boolean terminado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.gc();
        

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(this);
        String opcion=pref.getString("firstrun","0");
        if(opcion.equals("0")) {

            final DataBase dataBase = new DataBase();


            Thread logo=new Thread(){
                public void run(){
                    try{
                        int tiempo=0;
                        while(tiempo<5000){
                            sleep(100);
                            tiempo=tiempo+100;
                        }
                        if(terminado == true) {
                            dataBase.cancel(true);
                            Intent i = new Intent(MainActivity.this, InicioActivity.class);
                            startActivity(i);
                        }
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        finish();
                    }

                }
            };
            logo.start();

            dataBase.execute();



        }else{

            Thread logo=new Thread(){
                public void run(){
                    try{
                        int tiempo=0;
                        while(tiempo<3500){
                            sleep(100);
                            tiempo=tiempo+100;
                        }
                        Intent i=new Intent(MainActivity.this, InicioActivity.class);
                        startActivity(i);
                    }
                    catch(InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        finish();
                    }

                }
            };
            logo.start();

        }


    }

    //almacena las preferencias en la configuracion
    public void almacenar_pref_modo(String opcion){
        SharedPreferences prefs=PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("firstrun", opcion);
        editor.commit();

    }

    public void crear_frases(){
        String datos = "Todos los días hay que luchar porque ese amor a la humanidad viviente se transforme en hechos concretos, en actos que sirvan de ejemplo, de movilización.#Che\n" +
                "El hombre se pone al servicio de los demás; cuando lucha sin tregua por el bien común#José Martí\n" +
                "Haber servido mucho obliga a continuar sirviendo.#José Martí\n" +
                "Vela por el curso y el destino de esta Revolución la América entera. Toda ella tiene sus ojos puestos en nosotros. Toda ella nos respaldará en nuestros momentos difíciles.  Esta alegría de hoy no solo es en Cuba, sino en América entera.#Fidel Castro\n" +
                "A las santiagueras y santiagueros, les ratifico que edificaremos, en primer lugar con su participación directa, una ciudad cada vez más bella, higiénica, ordenada y disciplinada, a la altura de su condición heroína de Cuba: cuna de la Revolución.#Raúl Castro\n" +
                "En materia de salud, hasta un gesto visiblemente humano y conmovedor puede aliviar una cura y enrumbar un alma. Después de veinte siglos de ciencia, la sensibilidad sigue siendo la mejor medicina.#Joaquín Matorel\n" +
                "Brindar servicios de salud de excelencia, es dar al paciente todas las opciones posibles para su adecuado tratamiento. Es tratarlo como haríamos con nuestra propia familia, aconsejarlo, dedicarse y curarlo. Es realizar intervenciones de calidad, eficaces, seguras, cuándo y dónde se necesitan, evitando al máximo el desperdicio de recursos, porque la salud en Cuba es gratis, pero cuesta.#Lázaro Expósito\n" +
                "Si no eres parte de la solución, eres parte del problema, ¡actúa!#Lenin\n" +
                "La atención a la población, es el arte de ser cortés y ofrecer un servicio de calidad.#Lázaro Expósito\n" +
                "Recorro los pueblos y las aldeas, en ocasiones por una carretera, otras por un terraplén, otras por una guardarraya y otras por un trillo donde no hay ni guardarraya. Es la única manera de saber, de ver. Creo que eso lo debemos hacer todos, para poder tomar una conciencia clara de un problema; no es lo mismo oír decir de un problema, que le cuenten de una situación, a verlo en concreto.#Fidel Castro\n" +
                "Un trabajador de vanguardia, siente todos los trabajos que se llaman sacrificio con un interés nuevo, como una parte de su deber, pero no de su deber impuesto, sino de su deber interno y lo hace con interés.#Che\n" +
                "La agricultura es la única fuente constante, cierta y enteramente pura de riquezas.#José Martí\n" +
                "Los pueblos son grandes, no por el tamaño de su territorio, ni por el número de sus habitantes. Ellos son grandes, cuando sus hombres tienen conciencia cívica y fuerza moral suficiente, que los haga dignos de civilización y cultura.#Víctor Hugo\n" +
                "… el camino del progreso no es ni rápido ni fácil… Sólo el hombre puede cambiar los hechos, con pequeños gestos demostramos grandes acciones.#José Martí\n" +
                "El trabajo es de todos, y el compromiso es con todos…#José Martí\n" +
                "Desciende a las profundidades de ti mismo, y logra ver tu alma buena. La felicidad la hace solamente uno mismo con la buena conducta…#Albert Einstein\n" +
                "… Hay que saber cultivar la vergüenza de los hombres, hay que saber cultivar el honor de los hombres, la dignidad de los hombres, los mejores atributos que tiene el hombre son los valores…#Fidel Castro\n" +
                "… Se es bueno porque sí; y porque en el corazón se siente como un gusto la necesidad de sembrar y cultivar humanidad en el hombre para que nazca, eche raíces y se multiplique, funda una cultura de los valores, imprescindible para la convivencia social y para el propio despliegue de las energías creadoras que el hombre lleva en sí y desarrolla en función de la sociedad…#José Martí\n" +
                "…Regala un pescado a un hombre y le darás alimento para un día; enséñale a pescar, y lo alimentarás para el resto de su vida…#Proverbio chino\n" +
                "Una ciudad es un mosaico infinito de historias por contar, iguales y diferentes. Así es Santiago de Cuba. Cada imagen vale por mil palabras, cada palabra es capaz de evocar mil imágenes.#Reinaldo Cedeño\n" +
                "Se puede honrar a Martí citando sus frases bonitas, frases perfectas, y sobre todo, frases justas. Pero se puede y se debe honrar a Martí, en la forma en que él quería que se le hiciera, cuando decía a pleno pulmón: La mejor manera de decir, es hacer.#Che\n" +
                "El Agua es el Oro de nuestro tiempo en la Tierra, ahorrémosla.#Proverbio popular\n" +
                "Una ciudad se construye y moderniza con el sudor como cimiento y la conciencia de su gente haciendo de fuerza motriz…#Fidel Castro\n" +
                "Los atletas revolucionarios nacen del pueblo y se deben a él, sus éxitos y fracasos se viven con emoción, gritos de alegría, lágrimas de felicidad y hasta el reproche oportuno. No hay nada más gratificante para un deportista digno, que al aplauso, el reconocimiento y la admiración de su pueblo…#Fidel Castro\n" +
                "Sandy apostó a la destrucción, al sometimiento y la desolación, pero no pudo con los santiagueros, porque nada puede doblegar la voluntad de un pueblo que no sabe rendirse, porque nada puede derrotar el temple de hombres y mujeres que cargan sobre sus hombros, la dignidad de vivir en este pedazo de Cuba…#Lázaro Expósito\n" +
                "Dura es nuestra lucha, pero más duro debe ser el temple de acero de nuestras almas… Pareciera que esta tierra es el sitio ideal para enrumbar los hilos de la historia.  A los de aquí les brota de la piel el olor a dignidad y la proeza…Tengo el presentimiento de que no saben fallar.#Fidel Castro\n" +
                "Es nuestra Ideología las que nos hace fuertes e invencibles.#Fidel Castro\n" +
                "Los trabajadores de ómnibus urbanos tienen que ser la vanguardia, y esa vanguardia tiene que poseer un espíritu fuerte, un espíritu alerta y una gran capacidad de sacrificio.#Fidel Castro\n" +
                "Las revoluciones sólo avanzan y perduran cuando las lleva adelante el pueblo. Haber comprendido esa verdad y actuado invariablemente en consecuencia con ella, ha sido factor decisivo de la victoria de la Revolución cubana frente a enemigos, dificultades y retos, lo que la hace cada día más invencibles.#Fidel Castro\n" +
                "La calidad de los servicios al cliente, comienza y termina en nuestra propia conciencia.#Proverbio popular\n" +
                "La buena salud no es algo que podemos comprar.  Sin embargo, a veces está en nuestras manos evitar ciertas enfermedades…#Fidel Castro\n" +
                "Debemos incrementar la disciplina, actuar enérgicamente dentro del marco de la ley contra quienes incumplen lo establecido y a ser constantes en la higienización de nuestras ciudades…#Fidel Castro\n" +
                "La lucha por la calidad de un producto es una lucha revolucionaria y de vanguardia, calidad es lo que debemos darle al pueblo, es una obligación nuestra, una obligación de cada uno como parte de nuestro deber social.#Che\n" +
                "Emprenderemos la marcha, y perfeccionaremos lo que debamos perfeccionar, con lealtad meridiana y la fuerza unida, como Martí, Maceo y Gómez en marcha indetenible…#Fidel Castro\n" +
                "Gobernar es prever. Tenemos que aprender a prever para evitarnos bastantes problemas.#José Martí\n" +
                "Mientras más grave sea un problema más decidida debe ser nuestra acción…#Fidel Castro\n" +
                "Nada es imposible para los que luchan…#Fidel Castro\n" +
                "Cuando surja un problema práctico, en cualquier lugar, los cuadros en los distintos niveles actúen con prontitud e intencionalidad y no volvamos a dejarle al tiempo su solución…#Fidel Castro\n" +
                "Ser tan prudentes en el éxito como firmes  en la adversidad, es un principio que no puede olvidarse.#Fidel Castro\n" +
                "Ese es el Fidel invicto que nos convoca con su ejemplo y con la demostración de que ¡Sí se pudo, sí se puede y sí se podrá!  Superar cualquier obstáculo, amenaza o turbulencia en nuestro firme empeño de construir el socialismo en Cuba, o lo que es lo mismo, ¡Garantizar la independencia y la soberanía de la patria!#Raúl Castro\n" +
                "El optimismo conduce al éxito. Nada puede hacerse sin esperanza y confianza.#Proverbio popular\n" +
                "La única forma de impulsar las tareas, es yendo delante de ellas, es mostrandocon el ejemplo cómo se hacen, no diciendo desde atrás cómo se deben hacer…#Che\n" +
                "El socialismo es la ciencia del ejemplo.#Fidel Castro\n" +
                "Creer en los jóvenes es ver en ellos además de entusiasmo, capacidad; además de energía, responsabilidad; además de juventud, ¡pureza, heroísmo, carácter, voluntad, amor a la patria, fe en la patria!, ¡amor a la Revolución, fe en la Revolución, confianza en sí mismos!, convicción profunda de que la juventud puede, de que la juventud es capaz, convicción profunda de que sobre los hombros de la juventud se pueden depositar grandes tareas…#Fidel Castro\n" +
                "Si los jóvenes fallan, todo fallará. Es mi más profunda convicción que la juventud cubana luchará por impedirlo. Creo en ustedes.#Fidel Castro\n" +
                "Frente a cualquier dificultad objetiva, los hombres deben crecerse. La vida presenta innumerables alternativas y acciones posibles frente a cada problema en concreto. Lo que no debe nunca es aceptarse ninguna excusa para dejar de cumplir Ia tarea, ni permitir que Ias dificultades se conviertan en pretextos justificativos y conformistas.#Fidel Castro\n" +
                "El problema de las personas y los países es la pereza para encontrar las salidas y soluciones. Sin crisis no hay desafíos, sin desafíos la vida es una rutina, una lenta agonía. Sin crisis no hay méritos. Es en la crisis donde aflora lo mejor de cada uno, porque sin crisis todo viento es caricia. Hablar de crisis es promoverla, y callar en la crisis es exaltar el conformismo. En vez de esto trabajemos duro, acabemos de una vez con la única crisis amenazadora que es la tragedia de no querer luchar por superarla.#Albert Einstein\n" +
                "La lectura es una conversación con los hombres más ilustres de los siglos pasados.#Proverbio popular\n" +
                "Todos somos muy ignorantes. Lo que ocurre es que no todos ignoramos las mismas cosas.#Proverbio popular\n" +
                "Vivimos en el mundo cuando amamos. Sólo una vida vivida para los demás merece la pena ser vivida.#Proverbio popular\n" +
                "La formulación de un problema, es más importante que su solución.#Proverbio popular\n" +
                "Hacer patria constantemente es el deber de los jóvenes. Renovar a todo momento el recuerdo de los que han sido con gloria nuestros precursores, es lo que no debemos olvidar jamás.#Emilio Bacardí\n" +
                "El secreto para lograr mayores éxitos está en la capacitación de los cuadros para abarcar de conjunto la complejidad de la situación, establecer las prioridades, organizar el trabajo, cohesionar las fuerzas, exigir disciplina, educar con el ejemplo, explicar la necesidad de cada tarea, comunicar, entusiasmar, levantar el espíritu y movilizar la voluntad de la gente.#Raúl Castro\n" +
                "En lugar de ser un hombre de éxito, busca ser un hombre valioso: lo demás llegará naturalmente.” (Albert Einstein)\n" +
                "Parte todo de una fe, la fe en el hombre, y si existe fe en el hombre, entonces se tiene la conviccion de que no existen sueños ni utopias que no puedan realizarse.#Fidel Castro Ruz\n" +
                "Es fácil ser heroíco y generoso en un momento determinado, lo que cuesta es ser fiel y constante.#Carlos Marx\n" +
                "El que no posee el don de maravillarse ni de entusiasmarse más le valdría estar muerto, porque sus ojos están cerrados.#Proverbio popular\n" +
                "El futuro tiene muchos nombres. Para los débiles es lo inanlcanzable. Para los temerosos, lo desconocido. Para los valientes es la oportunidad.#Victor Hugo\n" +
                "Solamente aquél que contribuye al futuro tiene derecho a juzgar el pasado.#Friedrich Nietzsche\n" +
                "Primero mi honor y después la vida.#Antonio Maceo\n" +
                "Las ideas, como los árboles, han de venir de larga raíz y ser de suelo afín, para que prendan y prosperen.#José Martí\n" +
                "Comienza a manifestarse Ia madurez cuando sentimos que nuestra preocupación es mayor por los demás, que por nosotros mismos.#Albert Einstein\n" +
                "Si no existe organización y el control, Ias ideas, después del primer impulso van perdiendo eficacia, van cayendo en Ia rutina, van cayendo en el conformismo y acaban por ser simplemente un recuerdo.#Che\n" +
                "No es perezoso sólo el que no hace nada, sino también el que pudiendo hacer algo mejor, no lo hace.#Sócrates\n" +
                "Tenemos el deber sagrado de perfeccionar todo lo que hacemos, perfeccionar la Revolución, tenemos el deber sagrado de no estar satisfecho jamás.#Fidel Castro\n" +
                "Mientras haya obra que hacer, un hombre entero no tiene derecho a reposar, preste cada hombre, sin que nadie lo regañe, el servicio que lleve en sí.#José Martí\n" +
                "Si buscas resultados diferentes, no sigas haciendo  lo mismo.#Albert Einstein\n" +
                "Hay tres cosas que nos fortalecen: Ia expresión de lo que hemos querido ser, de lo que somos y de lo que seguiremos siendo siempre: revolucionarios.#Fidel Castro\n" +
                "Quiero vivir para mi Patria aunque me tropiece con la incomprensión de muchos y la indiferencia de algunos. Aunque me lancen al camino miles de obstáculos, aunque nos abandonen en el trayecto algunos de los que emprendieron la senda con nosotros, aunque otros se desvíen y pretendan lanzarse hacia otro derroteros. La meta es conocida y hacia ella hemos dirigido la  proa. Nada ni Nadie nos hará virar el rumbo. #René Ramos Latour\n" +
                "Donde son más altas Ias palmas en Cuba: nació Heredia... En la infatigable Santiago.#José Martí\n" +
                "Trabajar duro es: saber identificar los problemas, enfrentamos y darle solución,  defender las ideas de la Revolución y el Socialismo; hacer más, controlar más, exigir más y criticar menos.#Raúl Castro\n" +
                "En la tierra hace falta personas que trabajen más y critiquen menos, que construyan más y destruyan menos, que prometan menos y resuelvan más que esperen recibir menos y den más, que digan mejor ahora que mañana.#Che\n" +
                "La vida es muy peligrosa. No por las personas que hacen el mal, sino por las que se sientan a ver lo que pasa.#Albert Einstein \n" +
                "Me lo contaron y lo olvidé; lo vi y lo entendí; lo hice y lo aprendí.#Confucio\n" +
                "El revolucionario verdadero está guiado por grandes sentimientos de amor.#Che\n" +
                "Muchos me dirán aventurero, y lo soy, sólo que de un tipo diferente y de los que ponen el pellejo para demostrar sus verdades.#Che\n" +
                "Pero la juventud tiene que crear. Una juventud que no crea es una anomalía.#Che\n" +
                "A los ignorantes los aventajan los que leen libros. A éstos, los que retienen lo leído. A éstos, los que comprenden lo leído. A éstos, los que ponen manos a la obra.#Refrán indio\n" +
                "Cuando el hombre cesa de crear, deja de existir.#Proverbio popular\n" +
                "Aprender sin reflexionar es malgastar la energía.#Confucio\n";
        String[] lista2 = datos.split("\n");
        Frases f = null;
        for (int i = 0; i < lista2.length; i++) {
            String[] split = lista2[i].split("#");
            if (split.length == 2) {
                String nombre = split[0];
                String autor = split[1];
                f = new Frases(nombre,autor);
                f.save();
            }
        }
    }

    public void crear_configuracion(){
        Configuracion conf=new Configuracion();
        conf.save();
    }

    public class DataBase extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            almacenar_pref_modo("1");
            crear_configuracion();

            crear_frases();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            terminado = true;
            super.onPostExecute(aVoid);
        }
    }




}
