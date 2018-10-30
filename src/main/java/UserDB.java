
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.lang.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

class UserDB {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String FLAG = "flag";

    private MongoCollection<Document> collec;

    public UserDB(MongoDatabase db) {
        collec = db.getCollection("userlist");
    }

    public void registration(String username, long useRid) {
        try {
            Document doublecheck = collec.find(eq(ID, useRid)).first();
            if (doublecheck == null) {
                Document doc = new Document()
                    .append(NAME, username).append(ID, useRid);
                collec.insertOne(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void query(String flag, long useRid) {

        try {
            collec.updateOne(eq(ID, useRid), set(FLAG, flag));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flagDelete(long useRid) {
        try {
            collec.updateOne(eq(ID, useRid), set(FLAG, ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int flagCheck(long useRid) {
        int check = 0;
        try {
            if (collec.find(and(eq(ID, useRid), eq(FLAG, "FIO"))) != null)
                check = 1;
            else if (collec.find(and(eq(ID, useRid), eq(FLAG, "Phone"))) != null)
                check = 2;
            else if (collec.find(and(eq(ID, useRid), eq(FLAG, "Address"))) != null)
                check = 3;
            else if (collec.find(and(eq(ID, useRid), eq(FLAG, "Mail"))) != null)
                check = 4;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return check;
    }
}

