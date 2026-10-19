BEGIN;

CREATE TABLE IF NOT EXISTS alerts (
    id UUID PRIMARY KEY,
    source TEXT NOT NULL,
    source_event_id TEXT NOT NULL,
    resource TEXT NOT NULL,
    severity TEXT NOT NULL,
    occurred_at TIMESTAMPTZ NOT NULL,
    UNIQUE (source, source_event_id)
);

CREATE TABLE IF NOT EXISTS alert_outbox (
    event_id UUID PRIMARY KEY,
    message_key TEXT NOT NULL,
    payload JSONB NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    published_at TIMESTAMPTZ
);

COMMIT;