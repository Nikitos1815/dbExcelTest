
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.lang.*;

import static com.mongodb.client.model.Filters.eq;

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
    public void Query(){

    }

}
