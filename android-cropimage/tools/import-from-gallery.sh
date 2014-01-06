#!/bin/sh

set -e
set -u

IMPORT_LIST="${1}"
GALLERY_SRC="${2}"

test $# -eq 2 || {
    echo "Usage: ${0} <import-list> <gallery-source-root>"
    exit 1
}

test -f "${IMPORT_LIST}" || {
    echo "Unable to read import list"
    exit 1
}

test -d "${GALLERY_SRC}" || {
    echo "${GALLERY_SRC}: no such directory."
    exit 1
}

cat ${IMPORT_LIST} | while read line; do
    if [ ! -f "${GALLERY_SRC}/${line}" ]; then
        echo "Unable to find ${line}. Aborting."
        exit 1
    else
        echo "Importing: ${line}"
        install -D -m0644 "${GALLERY_SRC}/${line}" "${line}"
    fi
done
