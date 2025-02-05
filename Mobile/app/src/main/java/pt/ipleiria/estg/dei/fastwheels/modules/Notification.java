package pt.ipleiria.estg.dei.fastwheels.modules;

import java.time.LocalDateTime;

public class Notification {

    public static final String TITLE_WELCOME = "Bem vindo";
    public static final String TITLE_SYSTEM_UPDATE = "Atualização do Sistema";
    public static final String TITLE_SYSTEM_ERROR = "Erro no Sistema";
    public static final String TITLE_CHANGE_FAVORITE = "Alteração no favorito";
    public static final String TITLE_RESERVED_VEHICLE = "Seu veículo foi reservado";

    private int id;
    private String title;
    private String message;
    private LocalDateTime sendDate;
    private boolean read;
    private static int autoIncrementId = 1;

    public Notification(String title, String message) {
        this.id = autoIncrementId++;
        this.title = title;
        this.message = message;
        this.sendDate = LocalDateTime.now();
        this.read = false;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titulo) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String mensagem) {
        this.message = message;
    }

    public LocalDateTime getSendDate() {
        return sendDate;
    }

    public void setSendDate(LocalDateTime sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public void markAsUnread() {
        this.read = false;
    }

    @Override
    public String toString() {
        return message;
    }
}

