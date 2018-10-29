
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.lang.*;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class UserDB {

    protected  MongoClient mongoClient;
    protected MongoDatabase db;
    public UserDB(){
        mongoClient = new MongoClient( "localhost" , 27017 );
        db = mongoClient.getDatabase("userdb_queries");
    }



    public  void Registration(String username, long useRid){
        try{
            MongoCollection<org.bson.Document> collec = db.getCollection("userlist");
            org.bson.Document doublecheck = collec.find(eq("Id:", useRid)).first();
            if(doublecheck == null){
                org.bson.Document doc = new org.bson.Document()
                        .append("Name:",username).append("Id:", useRid);
                collec.insertOne(doc);
            }

        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }

    }
    public void Query(String flag,long useRid){

        try {
    MongoCollection<org.bson.Document> collec = db.getCollection("userlist");
    collec.updateOne(eq("Id:", useRid), set("Flag", flag));
             }
            catch (Exception e){
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
    }
    public void FlagDelete(long useRid){
        try {
            MongoCollection<org.bson.Document> collec = db.getCollection("userlist");
            collec.updateOne(eq("Id:", useRid), set("Flag", ""));
        }
        catch (Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
    public int FlagCheck(long useRid){
        int check = 0;
        try{
            MongoCollection<org.bson.Document> collec = db.getCollection("userlist");
            if(collec.find(and(eq("Id:",useRid),eq("Flag:","FIO"))) != null)
                check = 1;
            else if (collec.find(and(eq("Id:",useRid),eq("Flag:","Phone"))) != null)
                check = 2;
            else if(collec.find(and(eq("Id:",useRid),eq("Flag:","Address"))) != null)
                check = 3;
            else if(collec.find(and(eq("Id:",useRid),eq("Flag:","Mail"))) != null)
                check = 4;
        }
        catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        return check;
    }
    }

