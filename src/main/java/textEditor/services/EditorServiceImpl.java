package textEditor.services;

import org.springframework.stereotype.Service;
import textEditor.exceptions.LengthException;
import textEditor.exceptions.MixedUpException;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class EditorServiceImpl implements IEditorService {
    private static final ConcurrentLinkedQueue<String> UNDO = new ConcurrentLinkedQueue<>();
    private static final ConcurrentLinkedQueue<String> REDO = new ConcurrentLinkedQueue<>();
    private static final StringBuilder BUILDER = new StringBuilder("");
    public static final String BOLD_TAG_OPEN = "<b>";
    public static final String BOLD_TAG_CLOSE = "</b>";
    public static final String ITALIC_TAG_OPEN = "<i>";
    public static final String ITALIC_TAG_CLOSE = "</i>";
    public static final String UNDERLINE_TAG_OPEN = "<u>";
    public static final String UNDERLINE_TAG_CLOSE = "</u>";

    @Override
    public String add(String s) {
        final String newText = BUILDER.append(s).toString();
        UNDO.add(newText);
        return newText;
    }

    @Override
    public String add(String s, int position) {
        String newText;
        if (lengthProtection(position)) {
            newText = BUILDER.append(s).toString();
        } else {
            newText = BUILDER.insert(position, s).toString();
        }
        UNDO.add(newText);
        return newText;
    }

    @Override
    public String remove(int fromPosition, int toPosition) throws MixedUpException, LengthException {
        mixedUpProtection(fromPosition, toPosition);
        if (toPosition > BUILDER.length()) {
            toPosition = BUILDER.length();
        }
        return BUILDER.delete(fromPosition, toPosition).toString();
    }

    @Override
    public String italic(int fromPosition, int toPosition) throws MixedUpException, LengthException {
        mixedUpProtection(fromPosition, toPosition);
        if (toPosition > BUILDER.length()) {
            toPosition = BUILDER.length();
        }
        BUILDER.insert(toPosition, ITALIC_TAG_CLOSE);
        BUILDER.insert(fromPosition, ITALIC_TAG_OPEN);
        UNDO.add(BUILDER.toString());
        return BUILDER.toString();
    }

    @Override
    public String bold(int fromPosition, int toPosition) throws MixedUpException, LengthException {
        mixedUpProtection(fromPosition, toPosition);
        if (toPosition > BUILDER.length()) {
            toPosition = BUILDER.length();
        }
        BUILDER.insert(toPosition, BOLD_TAG_CLOSE);
        BUILDER.insert(fromPosition, BOLD_TAG_OPEN);
        UNDO.add(BUILDER.toString());
        return BUILDER.toString();
    }

    @Override
    public String underline(int fromPosition, int toPosition) throws MixedUpException, LengthException {
        mixedUpProtection(fromPosition, toPosition);
        if (toPosition > BUILDER.length()) {
            toPosition = BUILDER.length();
        }
        BUILDER.insert(toPosition, UNDERLINE_TAG_CLOSE);
        BUILDER.insert(fromPosition, UNDERLINE_TAG_OPEN);
        UNDO.add(BUILDER.toString());
        return BUILDER.toString();
    }

    @Override
    public String redo() {
        if (REDO.isEmpty()) {
            return BUILDER.toString();
        }
        final String newText = REDO.poll();
        BUILDER.replace(0, BUILDER.length(), newText);
        UNDO.add(newText);
        return BUILDER.toString();
    }

    @Override
    public String undo() {
        if (UNDO.isEmpty()) {
            return BUILDER.toString();
        }
        REDO.add(BUILDER.toString());
        final String newText = UNDO.poll();
        BUILDER.replace(0, BUILDER.length(), newText);
        return BUILDER.toString();
    }

    @Override
    public String print() {
        return BUILDER.toString();
    }

    @Override
    public void clear() {
        BUILDER.delete(0, BUILDER.length());
        UNDO.clear();
        REDO.clear();
    }

    private void mixedUpProtection(int from, int to) throws MixedUpException, LengthException {
        if (from > BUILDER.length()) {
            throw new LengthException("from VALUE is bigger than a row length");
        }
        if (to < from) {
            throw new MixedUpException("mixed up FROM and TO values");
        }
    }

    private boolean lengthProtection(int from) {
        return from >= BUILDER.length();
    }
}
