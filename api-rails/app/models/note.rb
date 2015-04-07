class Note < ActiveRecord::Base
  belongs_to :device
  belongs_to :room
  belongs_to :user
end
