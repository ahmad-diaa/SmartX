class Note < ActiveRecord::Base
  belongs_to :device
  belongs_to :room
  belongs_to :user
  validates :body,  presence: true, length: { minimum: 1}
end
