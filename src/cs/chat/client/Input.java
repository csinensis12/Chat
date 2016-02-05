package cs.chat.client;

import cs.chat.message.Message;

/**
 * Created by szymo on 05/02/2016.
 */
interface Input {
    void sendMessage(Message msg);
    void getConnection();

}
