import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import javax.management.Query;
import java.util.*;
import java.lang.*;

import static java.lang.Math.toIntExact;



public class bot extends TelegramLongPollingBot {
    private String username;
    private long id;
    private int counter;
    private UserDB reg = new UserDB();

    @Override
    public void onUpdateReceived(Update update) {


        if (update.hasMessage() && update.getMessage().hasText()) {
            String message_text = update.getMessage().getText();
            long chat_id = update.getMessage().getChatId();
            this.username = update.getMessage().getChat().getUserName();
            this.id = update.getMessage().getChat().getId();
            reg.Registration(this.username,this.id);
            if (update.getMessage().getText().equals("/start")) {
                SendMessage message2 = Keyboard(chat_id);
            Keyboard(chat_id);
            counter = 1;
                try {
                    execute(message2); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else {
                switch(counter) {
                case 1:
                    SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chat_id)
                            .setText(DBexcel.exec("testfile.xls", message_text));
                    try {
                        execute(message); // Sending our message object to user
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                case 2:


                case 3:


                case 4:

               default:
                    SendMessage new_message = new SendMessage()
                            .setChatId(chat_id)
                            .setText("Введите /start или выберите пункт меню" + "\n");
                    try {
                        execute(new_message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

            }

            }
        }else if (update.hasCallbackQuery()){
            SendMessage message = Callback(update);
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            }
        }




    @Override
    public String getBotUsername() {
        // Return bot username
        // If bot username is @MyAmazingBot, it must return 'proswimbot'
        return "proswimbot";
    }

    @Override
    public String getBotToken() {
        // Return bot token from BotFather
        return "624724125:AAEZKWtsxN5AJUhXxg6AEIqpjWxopKCo268";
    }

    public static SendMessage Keyboard(long chat_id) {
        SendMessage message = new SendMessage() // Create a message object object
                .setChatId(chat_id)
                .setText("Добро пожаловать!");
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("ФИО").setCallbackData("FIO"));
        rowInline.add(new InlineKeyboardButton().setText("Номер телефона").setCallbackData("PhoneNumber"));

        rowInline2.add(new InlineKeyboardButton().setText("Адрес").setCallbackData("HomeAddress"));
        rowInline2.add(new InlineKeyboardButton().setText("E-mail").setCallbackData("E-mail"));

        // Set the keyboard to the markup
        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        // Add it to the message
        markupInline.setKeyboard(rowsInline);
        message.setReplyMarkup(markupInline);

        return message;
    }
    public static SendMessage Callback(Update update){
        // Set variables
        SendMessage new_message = new SendMessage();
        String call_data = update.getCallbackQuery().getData();
        long id = update.getMessage().getChat().getId();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        if (call_data.equals("FIO")) {

             new_message.setChatId(chat_id).setText("Введите ФИО" + "\n");

        }else if(call_data.equals("PhoneNumber")){

             new_message
                    .setChatId(chat_id)
                    .setText("Введите номер телефона" + "\n");

        }else if(call_data.equals("HomeAddress")){

             new_message
                    .setChatId(chat_id)
                    .setText("Введите адрес" + "\n");
            return new_message;
        }else if(call_data.equals("E-mail")){
             new_message
                    .setChatId(chat_id)
                    .setText("Введите E-mail" + "\n");

        }

        return new_message;
    }
}

