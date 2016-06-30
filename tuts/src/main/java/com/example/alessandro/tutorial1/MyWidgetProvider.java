package com.example.alessandro.tutorial1;

import java.util.Random;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

/**QUESTA CLASSE E' APPOSITAMENTE REALIZZATA PER AGGIORNARE IL WIDGET E FUNGE DA BROADCAST RECEIVER INFATTI SI METTE IN ASCOLTO
 * DEL TEMPO CHE PASSA O DEL CLICK DELL'UTENTE. IN QUANTO BROADCAST RECEIVER SARA' DICHIARATA NEL MANIFEST. SI PUO'
 * UTILIZZARE UN SERVICE PER AGGIORNARE IL WIDGET RICHIAMANDO QUESTA CLASSE COME SE FOSSE UN CLICK DELL'UTENTE,
 * SAREBBE UN'OTTIMA VARANTE. SE INTERESSATO GUARDA:
 * http://www.vogella.com/tutorials/AndroidWidgets/article.html**/


public class MyWidgetProvider extends AppWidgetProvider {

    private static final String ACTION_CLICK = "ACTION_CLICK";
    public String[] versetti = {"Non sia turbato il vostro cuore. Abbiate fede in Dio e abbiate fede in me.                                  \n" +
            "                                         Giovanni 14,1\n",
                                "Io sono la luce del mondo. Chi segue me non camminerà nelle tenebre ma avrà la luce della vita.\n" +
                                        "                                          Giovanni 8,12\n",
                                "Io sono il pane della vita; chi viene a me non avrà più fame e chi crede in me non avrà più sete.\n" +
                                        "                                        Giovanni 6,35\n",
                                "Non temere, perché io sono con te.\n" +
                                        "\n" +
                                        "                                              Isaia 43,5\n",
                                "Guariscimi, Signore, e io sarò guarito.\n" +
                                        "                                      Geremia 17,14\n",
                                "vi darò un cuore nuovo […] toglierò da voi il cuore di pietra e vi darò un cuore di carne.\n" +
                                        "                                 Ezechiele 36,25-26\n",
                                "Porrò il mio spirito dentro di voi.\n" +
                                        "\n" +
                                        "                                    Ezechiele 36,27\n",
                                "Ma subito Gesù parlò loro: Coraggio, sono io, non abbiate paura.\n" +
                                        "\n" +
                                        "                                        Matteo 14,27\n",
                                "Signore, nella tua volontà è la mia gioia.\n" +
                                        "\n" +
                                        "                                         Salmo 119,16\n",
                                "Il Signore risana i cuori affranti e fascia le loro ferite.\n" +
                                        "\n" +
                                        "                                            Salmo 147,3\n",
                                "Il Signore libera i prigionieri, il Signore ridona la vista ai ciechi, il Signore rialza chi è caduto, il Signore ama i giusti.                     Salmo 146,8",
                                "Gustate e vedete quanto è buono il Signore; beato l’uomo che in lui si rifugia.\n" +
                                        "                                               Salmo 34,9\n",
                                "Il Signore è vicino a chi ha il cuore ferito.\n" +
                                        "\n" +
                                        "                                           Salmo 34,19\n",
                                "Il mio Dio rischiara le mie tenebre.\n" +
                                        "\n" +
                                        "                                            Salmo 18,29\n",
                                "Di te ha detto il mio cuore: «Cercate il suo volto»; il tuo volto, Signore, io cerco.\n" +
                                        "                                              Salmo 27,8\n",
                                "In te, Signore, mi sono rifugiato, mai sarò deluso. Mi affido alle tue mani.\n" +
                                        "\n" +
                                        "                                           Salmo 31,2.6\n",
                                "Esulterò di gioia per la tua grazia, perché hai guardato alla mia miseria, hai conosciuto le mie angosce.                              Salmo 31,8",
                                "Come un padre ha pietà dei suoi figli, così il Signore ha pietà di quanti lo temono.\n" +
                                        "                                          Salmo 103,13\n",
                                "Tu sei buono, Signore, e perdoni, sei pieno di misericordia\n" +
                                        "con chi ti invoca.\n" +
                                        "                                             Salmo 86,5\n",
                                "Io sono il Signore tuo salvatore.\n" +
                                        "                                             \n" +
                                        "                                              Isaia 49,26\n",
                                "Confida sempre in lui, davanti a lui effondi il tuo cuore.\n" +
                                        "\n" +
                                        "                                             Salmo 62,9\n",
                                "Prima di formarti nel grembo materno, ti conoscevo, prima che tu uscissi alla luce, ti avevo consacrato;\n" +
                                        "                                            Geremia 1,5\n",
                                "Non ricordate più le cose passate, non pensate più alle cose antiche! Ecco, faccio una cosa nuova\n" +
                                        "                                       Isaia 43,18-19\n",
                                "Il Padre commosso gli corse incontro, gli si gettò al collo e lo baciò.\n" +
                                        "                                               Luca 15,20\n"
                                    };

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        // Get all ids
        ComponentName thisWidget = new ComponentName(context,MyWidgetProvider.class);
        int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
        for (int widgetId : allWidgetIds) {
            // create some random data
            int number = (new Random().nextInt(23));  //100

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            Log.w("WidgetExample", String.valueOf(number));
            // Set the text
           // remoteViews.setTextViewText(R.id.update, String.valueOf(number));
            remoteViews.setTextViewText(R.id.update, versetti[number]);

            // Register an onClickListener
            Intent intent = new Intent(context, MyWidgetProvider.class);

            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.update, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}