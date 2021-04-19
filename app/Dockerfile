FROM python:3.8

RUN pip install Flask gunicorn image

COPY / src/
WORKDIR /src

ENV PORT 8080

CMD exec gunicorn --bind :$PORT --workers 1 --threads 8 app:app