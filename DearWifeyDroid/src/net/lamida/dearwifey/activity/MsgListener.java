package net.lamida.dearwifey.activity;

import net.lamida.dearwifey.entity.Msg;

public interface MsgListener {
	void updateMessage(Msg m);
	void updateMessages(Msg m);
}
