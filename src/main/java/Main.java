import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.lang.*;

public class Main {
    public static void main(String[] args) {
        ApiContextInitializer.init();

        MongoClient client = new MongoClient("localhost", 27017);
        MongoDatabase database = client.getDatabase("userdb_queries");
        UserDB users = new UserDB(database);

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot(users));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}