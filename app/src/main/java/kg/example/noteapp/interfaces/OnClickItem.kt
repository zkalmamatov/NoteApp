package kg.example.noteapp.interfaces

import kg.example.noteapp.data.models.NoteModel

interface OnClickItem {

    fun onLongClick(noteModel: NoteModel)

    fun onClick(noteModel: NoteModel)

}