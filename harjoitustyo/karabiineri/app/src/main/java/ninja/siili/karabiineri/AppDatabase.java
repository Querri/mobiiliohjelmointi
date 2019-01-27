package ninja.siili.karabiineri;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import ninja.siili.karabiineri.interfaces.PlaceDao;
import ninja.siili.karabiineri.utilities.Converters;


@Database(entities = {Place.class}, version = 4)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();

    // volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration() // wipes and rebuilds instead of migrating
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    /** Override the onOpen method to populate the database. */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // populate again on every restart
            //new PopulateDbAsync(INSTANCE).execute();
        }
    };


    /** Populate the database in the background. */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final PlaceDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.placeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            // start the app with a clean database every time.
            // TODO Not needed if only populate on creation.
            mDao.deleteAll();
            mDao.insert(new Place(
                    "TeKiilan kiipeilyseinä",
                    "TTY:n kiipeilykerho TeKiilan ylläpitämä sisäseinä.",
                    "UnipoliSportin ja TeKiilan jäsenmaksut",
                    new LatLng(61.449969, 23.861945)));
            mDao.insert(new Place(
                    "TAMKin kiipeilyseinä",
                    "TAMKin liikuntajaoston ylläpitämä kiipeilyseinä.",
                    "UnipoliSportin jäsenmaksu, omat kengät ja mankka ja liidiköysi mukaan.",
                    new LatLng(61.503823, 23.806502)));
            return null;
        }
    }
}
