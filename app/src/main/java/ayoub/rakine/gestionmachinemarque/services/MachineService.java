package ayoub.rakine.gestionmachinemarque.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ayoub.rakine.gestionmachinemarque.beans.Machine;
import ayoub.rakine.gestionmachinemarque.beans.Marque;

public class MachineService {
    private Context context;

    private static final String TABLE_MACHINE = "machine";


    private static final String Id = "id";
    private static final String Reference = "reference";

    private static final String Prix = "prix";

    private static final String Date = "date";

    private static final String Marque_n = "marque_nom";

    private static String [] COLUMNS = {Id, Reference, Prix,Date,Marque_n};


    private MyDataBaseHelper helper;


    public MachineService(Context context) {
        this.helper = new MyDataBaseHelper(context);
        this.context = context;
    }

    public MachineService() {
        this.helper = new MyDataBaseHelper(context);
    }

    public void create(Machine e) {
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Reference, e.getReference());
        values.put(Prix, e.getPrix());
        values.put(Date, e.getDate());
        values.put(Marque_n, e.getMarqueCode());
        long result=db.insert(TABLE_MACHINE,
                null,
                values);
        if(result == -1){
            Toast.makeText(context, "Failed to add this machine", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "new machine Added Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public List<Machine> findAll(){
        List<Machine> eds = new ArrayList<>();
        String req ="select * from "+TABLE_MACHINE;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c = db.rawQuery(req, null);
        Machine e = null;
        if(c.moveToFirst()){
            do{
                e = new Machine();
                e.setId(c.getInt(0));
                e.setReference(c.getString(1));
                e.setPrix(c.getInt(2));
                e.setDate(c.getString(3));
                e.setMarqueCode(c.getString(4));
                eds.add(e);
            }while(c.moveToNext());
        }
        return eds;
    }

    public void update(Machine e,int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Id, id);
        values.put(Reference, e.getReference());
        values.put(Prix, e.getPrix());
        values.put(Date, e.getDate());
        values.put(Marque_n, e.getMarqueCode());

        long result=db.update(TABLE_MACHINE,
                values,
                "id = ?",
                new String[]{String.valueOf(id)});
        if(result == -1){
            Toast.makeText(context, "Failed to update this machine", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "machine Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db = this.helper.getWritableDatabase();
        db.delete(TABLE_MACHINE,
                "id = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    public Machine findById(int id){
        Machine e = null;
        SQLiteDatabase db = this.helper.getReadableDatabase();
        Cursor c;
        c = db.query(TABLE_MACHINE,
                COLUMNS,
                "id = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        if(c.moveToFirst()){
            //Initialise un nouvel objet Etudiant pour stocker les informations de l'étudiant trouvé.
            e = new Machine();
            e.setId(c.getInt(0));
            e.setReference(c.getString(1));
            e.setPrix(c.getInt(2));
            e.setDate(c.getString(3));
            e.setMarqueCode(c.getString(4));
        }

        db.close();
        return e;
    }
}

