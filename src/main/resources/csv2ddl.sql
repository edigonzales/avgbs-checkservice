SELECT 
    NBIdent AS identnd,
    Gemeinde AS municipality,
    ROUND("BFS-Nr") AS fosnr,
    CASE 
        WHEN trim("zuständig") = 'BSB + Partner' THEN 4
        WHEN trim("zuständig") = 'W+H AG' THEN 8
        WHEN trim("zuständig") = 'Emch+Berger AG Vermessungen' THEN 7
        WHEN trim("zuständig") = 'Sutter Ingenieur- und Planungsbüro AG' THEN 6
        WHEN trim("zuständig") = 'Lerch Weber AG' THEN 5
    END AS role_id,
    'true' AS enabled
FROM
    nummerierungsbereiche
ORDER BY
   "BFS-Nr" 
;

-- Nicht sicher, ob das funktioniert, wegen
-- der String-/Integerwerten.
-- Definitiv müsste auto-increment gesetzt werden.


-- Anschliessend im INSERT-SQL:
-- Tabelle umbenennen
-- 'true' -> true