package textEditor.services;

import textEditor.exceptions.LengthException;
import textEditor.exceptions.MixedUpException;

public interface IEditorService {
    String add(String s);
    String add(String s, int position);
    String remove(int fromPosition, int toPosition) throws MixedUpException, LengthException;
    String italic (int fromPosition, int toPosition) throws MixedUpException, LengthException;
    String bold (int fromPosition, int toPosition) throws MixedUpException, LengthException;
    String underline (int fromPosition, int toPosition) throws MixedUpException, LengthException;
    String redo();
    String undo();
    String print();
    void clear();
}
