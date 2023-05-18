import {useState} from "react";
import {PostNewMessage} from "./components/PostNewMessage";
import {Messages} from "./components/Messages";

export const MessagesPage = () => {
    const [messagesClick, setMessagesClick] = useState(false);

    return (
        <div className="container">
            <div className="mt-3 mb-2">
                <nav>
                    <div className="nav nav-tabs" id="nav-tab" role="tablist">
                        <button className="nav-link active" id="nav-messages-tab" data-bs-toggle="tab"
                                data-bs-target="#nav-send-messages" type="button" role="tab"
                                aria-controls="nav-send-messages" aria-selected="true"
                                onClick={() => setMessagesClick(false)}>
                            Submit Question
                        </button>
                        <button className="nav-link" id="nav-messages-tab" data-bs-toggle="tab"
                                data-bs-target="#nav-messages" type="button" role="tab"
                                aria-controls="nav-messages" aria-selected="false"
                                onClick={() => setMessagesClick(true)}>
                            Q/A Response/Pending
                        </button>
                    </div>
                </nav>
                <div className="tab-content" id="nav-tabContent">
                    <div className="tab-pane fade show active" id="nav-send-messages" role="tabpanel"
                         aria-labelledby="nav-send-messages-tab">
                        <PostNewMessage />
                    </div>
                    <div className="tab-pane fade" id="nav-messages" role="tabpanel"
                         aria-labelledby="nav-messages-tab">
                        {messagesClick ? <Messages /> : <> </>}
                    </div>
                </div>
            </div>
        </div>
    );
}