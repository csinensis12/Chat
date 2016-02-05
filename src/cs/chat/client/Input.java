package cs.chat.client;

import cs.chat.message.Message;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
interface Input {
    String getMessage();
    String getReceiver();
    String getIp();
    int getPort();
    String getClientName();
}
