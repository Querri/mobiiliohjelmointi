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

@Database(entities = {Place.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlaceDao placeDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Override the onOpen method to populate the database.
     * For this sample, we clear the database every time it is created or opened.
     *
     * If you want to populate the database only when the database is created for the 1st time,
     * override RoomDatabase.Callback()#onCreate
     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PlaceDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.placeDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {

            LatLng loc = new LatLng(61.449969, 23.861945);
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.
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
