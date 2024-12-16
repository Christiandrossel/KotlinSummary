package net.avgl.ekz.onleihe;

public class SQL
{
}


/**
 * SELECT
 *     p.id,
 *     p.title,
 *     COALESCE(SUM(r.number_of_tickets), 0) AS reserved_tickets
 * FROM
 *     plays p
 * LEFT JOIN
 *     reservations r ON p.id = r.play_id
 * GROUP BY
 *     p.id, p.title
 * ORDER BY
 *     reserved_tickets DESC,
 *     p.id ASC;
 */