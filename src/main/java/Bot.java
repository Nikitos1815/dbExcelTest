import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


import java.util.*;
import java.lang.*;

public class Bot extends TelegramLongPollingBot {
    private UserDB db;

    public Bot(UserDB db) {
        this.db = db;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String username = update.getMessage().getChat().getUserName();
            Long id = update.getMessage().getChat().getId();

            db.registration(username, id);

            if (update.getMessage().getText().equals("/start")) {
                SendMessage message2 = Keyboard(chatId);
                try {
                    execute(message2); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            } else {
                int counter = db.flagCheck(id);

                switch (counter) {
                    case 1:
                        SendMessage message = new SendMessage() // Create a message object object
                            .setChatId(chatId)
                            .setText(DBexcel.exec("testfile.xls", messageText));
                        db.flagDelete(id);
                        try {
                            execute(message); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 2:
                        SendMessage message1 = new SendMessage() // Create a message object object
                                .setChatId(chatId)
                                .setText("Here will be phone number");
                        try {
                            execute(message1); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        SendMessage message2 = new SendMessage() // Create a message object object
                                .setChatId(chatId)
                                .setText("Here will be Address");
                        try {
                            execute(message2); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                    case 4:
                        SendMessage message3 = new SendMessage() // Create a message object object
                                .setChatId(chatId)
                                .setText("Here will be phone number");
                        try {
                            execute(message3); // Sending our message object to user
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 0:
                        SendMessage new_message = new SendMessage()
                            .setChatId(chatId)
                            .setText("Введите /start или выберите пункт меню" + "\n");
                        try {
                            execute(new_message);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;

                }

            }
        } else if (update.hasCallbackQuery()) {
            Long id = update.getCallbackQuery().getMessage().getChat().getId();

            SendMessage message = Callback(update);
            switch (message.getText()) {
                case "Введите ФИО":
                    db.query("FIO", id);
                    break;
                case "Введите номер телефона":
                    db.query("Phone", id);
                    break;
                case "Введите адрес":
                    db.query("Address", id);
                    break;
                case "Введите E-mail":
                    db.query("Mail", id);
                    break;
            }
            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public String getBotUsername() {
        // Return Bot username
        // If Bot username is @MyAmazingBot, it must return 'proswimbot'
        return "proswimbot";
    }

    @Override
    public String getBotToken() {
        // Return Bot token from BotFather
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

    public static SendMessage Callback(Update update) {
        // Set variables
        SendMessage new_message = new SendMessage();
        String call_data = update.getCallbackQuery().getData();
        long chat_id = update.getCallbackQuery().getMessage().getChatId();
        if (call_data.equals("FIO")) {

            new_message.setChatId(chat_id).setText("Введите ФИО");

        } else if (call_data.equals("PhoneNumber")) {

            new_message
                .setChatId(chat_id)
                .setText("Введите номер телефона");

        } else if (call_data.equals("HomeAddress")) {

            new_message
                .setChatId(chat_id)
                .setText("Введите адрес");
            return new_message;
        } else if (call_data.equals("E-mail")) {
            new_message
                .setChatId(chat_id)
                .setText("Введите E-mail");

        }

        return new_message;
    }

}

