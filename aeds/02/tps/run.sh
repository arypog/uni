#!/bin/bash


if [ -z "$1" ]; then
    echo "Usage: $0 <source-file>"
    exit 1
fi

FILE="$1"
EXT="${FILE##*.}"
BASENAME="${FILE%.*}"

OUTDIR="../bin/"
mkdir -p "$OUTDIR"

case "$EXT" in
    c)
        OUTPUT="$OUTDIR$(basename $BASENAME)"
        gcc -o "$OUTPUT" "$FILE"
        if [ $? -eq 0 ]; then
            "$OUTPUT"
        else 
            echo "C Compilation failed."
        fi
        ;;
    java) 
        javac -d "$OUTDIR" "$FILE"
        if [ $? -eq 0 ]; then
            CLASS_NAME=$(basename "$BASENAME") # Extract class name
            java -cp "$OUTDIR" "$CLASS_NAME"
        else
            echo "Java Compilation failed."
        fi
        ;;
    *)
        echo "Unsupported file type: $EXT"
        exit 1
        ;;
esac