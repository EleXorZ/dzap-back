-- ============================================
-- QUIZ INVESTISSEUR IMMOBILIER - DATA INIT
-- ============================================
-- Ce fichier initialise les 10 questions du quizz
-- avec leurs réponses et les scores par profil
-- Profils: PRUDENT, BALANCED, DYNAMIC, PATRIMONIAL

-- ============================================
-- QUESTION 1 : Objectif principal
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (1, 'Quel est votre objectif principal en investissant dans l''immobilier ?', 1, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(1, 1, 'Générer des revenus complémentaires stables et sécurisés', 1),
(2, 1, 'Constituer un patrimoine équilibré entre rendement et sécurité', 2),
(3, 1, 'Maximiser mes rendements, quitte à prendre des risques', 3),
(4, 1, 'Préparer ma succession et optimiser ma fiscalité', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(1, 'PRUDENT', 3), (1, 'BALANCED', 1), (1, 'DYNAMIC', 0), (1, 'PATRIMONIAL', 1),
(2, 'PRUDENT', 1), (2, 'BALANCED', 3), (2, 'DYNAMIC', 1), (2, 'PATRIMONIAL', 1),
(3, 'PRUDENT', 0), (3, 'BALANCED', 1), (3, 'DYNAMIC', 3), (3, 'PATRIMONIAL', 0),
(4, 'PRUDENT', 1), (4, 'BALANCED', 1), (4, 'DYNAMIC', 0), (4, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 2 : Horizon d'investissement
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (2, 'Sur quel horizon de temps envisagez-vous votre investissement ?', 2, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(5, 2, 'Court terme (moins de 5 ans)', 1),
(6, 2, 'Moyen terme (5 à 10 ans)', 2),
(7, 2, 'Long terme (10 à 20 ans)', 3),
(8, 2, 'Très long terme (transmission aux enfants)', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(5, 'PRUDENT', 0), (5, 'BALANCED', 1), (5, 'DYNAMIC', 3), (5, 'PATRIMONIAL', 0),
(6, 'PRUDENT', 2), (6, 'BALANCED', 3), (6, 'DYNAMIC', 1), (6, 'PATRIMONIAL', 0),
(7, 'PRUDENT', 3), (7, 'BALANCED', 2), (7, 'DYNAMIC', 1), (7, 'PATRIMONIAL', 2),
(8, 'PRUDENT', 1), (8, 'BALANCED', 1), (8, 'DYNAMIC', 0), (8, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 3 : Tolérance au risque
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (3, 'Comment réagiriez-vous si la valeur de votre bien baissait de 15% ?', 3, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(9, 3, 'Je serais très inquiet et envisagerais de vendre', 1),
(10, 3, 'Je serais préoccupé mais je garderais le bien', 2),
(11, 3, 'C''est temporaire, j''achèterais peut-être un autre bien', 3),
(12, 3, 'La valeur m''importe peu, je me concentre sur les loyers et la transmission', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(9, 'PRUDENT', 3), (9, 'BALANCED', 0), (9, 'DYNAMIC', 0), (9, 'PATRIMONIAL', 0),
(10, 'PRUDENT', 2), (10, 'BALANCED', 3), (10, 'DYNAMIC', 0), (10, 'PATRIMONIAL', 1),
(11, 'PRUDENT', 0), (11, 'BALANCED', 1), (11, 'DYNAMIC', 3), (11, 'PATRIMONIAL', 0),
(12, 'PRUDENT', 1), (12, 'BALANCED', 1), (12, 'DYNAMIC', 1), (12, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 4 : Implication dans la gestion
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (4, 'Quel niveau d''implication êtes-vous prêt à avoir dans la gestion ?', 4, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(13, 4, 'Aucune, je veux déléguer totalement', 1),
(14, 4, 'Limitée, je peux superviser une agence', 2),
(15, 4, 'Active, je veux gérer moi-même pour optimiser', 3),
(16, 4, 'Je veux structurer un montage familial avec mes proches', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(13, 'PRUDENT', 3), (13, 'BALANCED', 1), (13, 'DYNAMIC', 0), (13, 'PATRIMONIAL', 1),
(14, 'PRUDENT', 1), (14, 'BALANCED', 3), (14, 'DYNAMIC', 1), (14, 'PATRIMONIAL', 1),
(15, 'PRUDENT', 0), (15, 'BALANCED', 1), (15, 'DYNAMIC', 3), (15, 'PATRIMONIAL', 0),
(16, 'PRUDENT', 0), (16, 'BALANCED', 1), (16, 'DYNAMIC', 1), (16, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 5 : Budget disponible
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (5, 'Quel est votre budget approximatif pour investir ?', 5, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(17, 5, 'Moins de 50 000€', 1),
(18, 5, 'Entre 50 000€ et 150 000€', 2),
(19, 5, 'Entre 150 000€ et 400 000€', 3),
(20, 5, 'Plus de 400 000€', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(17, 'PRUDENT', 2), (17, 'BALANCED', 2), (17, 'DYNAMIC', 1), (17, 'PATRIMONIAL', 0),
(18, 'PRUDENT', 2), (18, 'BALANCED', 3), (18, 'DYNAMIC', 2), (18, 'PATRIMONIAL', 1),
(19, 'PRUDENT', 1), (19, 'BALANCED', 2), (19, 'DYNAMIC', 3), (19, 'PATRIMONIAL', 2),
(20, 'PRUDENT', 0), (20, 'BALANCED', 1), (20, 'DYNAMIC', 2), (20, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 6 : Expérience en immobilier
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (6, 'Quelle est votre expérience en investissement immobilier ?', 6, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(21, 6, 'Aucune, c''est mon premier investissement', 1),
(22, 6, 'J''ai déjà ma résidence principale', 2),
(23, 6, 'J''ai 1 ou 2 biens locatifs', 3),
(24, 6, 'J''ai un patrimoine immobilier conséquent (3+ biens)', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(21, 'PRUDENT', 3), (21, 'BALANCED', 2), (21, 'DYNAMIC', 0), (21, 'PATRIMONIAL', 0),
(22, 'PRUDENT', 2), (22, 'BALANCED', 3), (22, 'DYNAMIC', 1), (22, 'PATRIMONIAL', 1),
(23, 'PRUDENT', 1), (23, 'BALANCED', 2), (23, 'DYNAMIC', 3), (23, 'PATRIMONIAL', 2),
(24, 'PRUDENT', 0), (24, 'BALANCED', 1), (24, 'DYNAMIC', 2), (24, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 7 : Type de rendement préféré
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (7, 'Quel type de rendement vous attire le plus ?', 7, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(25, 7, 'Rendement modéré (3-5%) mais très sécurisé', 1),
(26, 7, 'Rendement correct (5-7%) avec une gestion raisonnable', 2),
(27, 7, 'Rendement élevé (8%+) même si cela demande plus d''efforts', 3),
(28, 7, 'Le rendement immédiat m''importe peu, je vise les avantages fiscaux', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(25, 'PRUDENT', 3), (25, 'BALANCED', 1), (25, 'DYNAMIC', 0), (25, 'PATRIMONIAL', 1),
(26, 'PRUDENT', 1), (26, 'BALANCED', 3), (26, 'DYNAMIC', 1), (26, 'PATRIMONIAL', 1),
(27, 'PRUDENT', 0), (27, 'BALANCED', 1), (27, 'DYNAMIC', 3), (27, 'PATRIMONIAL', 0),
(28, 'PRUDENT', 1), (28, 'BALANCED', 1), (28, 'DYNAMIC', 0), (28, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 8 : Situation fiscale
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (8, 'Comment décririez-vous votre situation fiscale actuelle ?', 8, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(29, 8, 'TMI faible (0-11%), je cherche surtout à me constituer un patrimoine', 1),
(30, 8, 'TMI moyen (30%), je veux équilibrer rendement et fiscalité', 2),
(31, 8, 'TMI élevé (41%+), l''optimisation fiscale est importante', 3),
(32, 8, 'Je suis soumis à l''IFI et je cherche à optimiser ma transmission', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(29, 'PRUDENT', 2), (29, 'BALANCED', 2), (29, 'DYNAMIC', 2), (29, 'PATRIMONIAL', 0),
(30, 'PRUDENT', 1), (30, 'BALANCED', 3), (30, 'DYNAMIC', 2), (30, 'PATRIMONIAL', 1),
(31, 'PRUDENT', 0), (31, 'BALANCED', 1), (31, 'DYNAMIC', 2), (31, 'PATRIMONIAL', 3),
(32, 'PRUDENT', 0), (32, 'BALANCED', 0), (32, 'DYNAMIC', 1), (32, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 9 : Type de bien préféré
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (9, 'Quel type de bien vous attire le plus ?', 9, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(33, 9, 'Un appartement dans une grande ville, facile à louer', 1),
(34, 9, 'Un bien avec travaux pour créer de la valeur', 2),
(35, 9, 'Un immeuble de rapport pour maximiser le rendement', 3),
(36, 9, 'Un bien de prestige ou familial à transmettre', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(33, 'PRUDENT', 3), (33, 'BALANCED', 2), (33, 'DYNAMIC', 0), (33, 'PATRIMONIAL', 1),
(34, 'PRUDENT', 0), (34, 'BALANCED', 2), (34, 'DYNAMIC', 3), (34, 'PATRIMONIAL', 0),
(35, 'PRUDENT', 0), (35, 'BALANCED', 1), (35, 'DYNAMIC', 3), (35, 'PATRIMONIAL', 1),
(36, 'PRUDENT', 1), (36, 'BALANCED', 1), (36, 'DYNAMIC', 0), (36, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- QUESTION 10 : Mode de financement
-- ============================================
INSERT INTO quiz_questions (id, question_text, order_index, active, created_at)
VALUES (10, 'Comment envisagez-vous de financer votre investissement ?', 10, true, NOW())
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answers (id, question_id, answer_text, order_index) VALUES
(37, 10, 'Plutôt comptant ou avec un faible emprunt', 1),
(38, 10, 'Un emprunt classique avec un bon apport (20-30%)', 2),
(39, 10, 'Maximum d''effet de levier, emprunt à 110%', 3),
(40, 10, 'Via une structure (SCI) pour optimiser la transmission', 4)
ON CONFLICT (id) DO NOTHING;

INSERT INTO quiz_answer_scores (answer_id, profile, score) VALUES
(37, 'PRUDENT', 3), (37, 'BALANCED', 1), (37, 'DYNAMIC', 0), (37, 'PATRIMONIAL', 1),
(38, 'PRUDENT', 2), (38, 'BALANCED', 3), (38, 'DYNAMIC', 1), (38, 'PATRIMONIAL', 1),
(39, 'PRUDENT', 0), (39, 'BALANCED', 1), (39, 'DYNAMIC', 3), (39, 'PATRIMONIAL', 0),
(40, 'PRUDENT', 0), (40, 'BALANCED', 1), (40, 'DYNAMIC', 1), (40, 'PATRIMONIAL', 3)
ON CONFLICT DO NOTHING;

-- ============================================
-- Mise à jour des séquences
-- ============================================
SELECT setval('quiz_questions_id_seq', (SELECT MAX(id) FROM quiz_questions));
SELECT setval('quiz_answers_id_seq', (SELECT MAX(id) FROM quiz_answers));

